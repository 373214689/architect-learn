package org.apache.spark.sql.structed.datasource.mysql

import org.apache.spark.internal.Logging
import org.apache.spark.sql.catalyst.InternalRow
import org.apache.spark.sql.catalyst.expressions.GenericInternalRow
import org.apache.spark.sql.execution.streaming.{Offset, Source}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{DataFrame, SQLContext}
import org.apache.spark.unsafe.types.UTF8String
import org.json4s.jackson.Serialization
import org.json4s.{Formats, NoTypeHints}

import java.sql.{Connection, DriverManager, Types}
import scala.collection.mutable.ArrayBuffer

class MySQLSource(sqlContext: SQLContext,
                  options: Map[String, String],
                  schemaOption: Option[StructType]) extends Source
  with Logging {

  val driverName: String = options.getOrElse("driverName", "com.mysql.cj.jdbc.Driver")
  val database: String = options("database")
  val tableName: String = options("table")
  val host: String = options("host")
  val port: Int = options.getOrElse("port", "3306").toInt
  val username: String = options("username")
  val password: String = options("password")
  val batchSize: Option[Long] = Option(options.getOrElse("batchSize", "100").toLong)
  val parameters: Map[String, String] = Map (
    "allowPublicKeyRetrieval" -> options.getOrElse("allowPublicKeyRetrieval", "false"),
    "autoReconnect" -> options.getOrElse("autoReconnect", "false"),
    "autoReconnectForPools" -> options.getOrElse("autoReconnectForPools", "false"),
    "characterEncoding" -> options.getOrElse("characterEncoding", "UTF-8"),
    "connectTimeout" -> options.getOrElse("connectTimeout", "3000"),
    "failOverReadOnly" -> options.getOrElse("failOverReadOnly", "false"),
    "initialTimeout" -> options.getOrElse("initialTimeout", "3000"),
    "maxReconnects" -> options.getOrElse("maxReconnects", "3"),
    "serverTimezone" -> options.getOrElse("serverTimezone", "UTC-8"),
    "useSSL" -> options.getOrElse("mysql.useSSL", "false"),
    "useUnicode" -> options.getOrElse("mysql.useUnicode", "false")
  )

  // mysql connection
  lazy val conn: Connection = initConnection
  // current offset
  var currentOffset: Long = 0L

  private[this] def initConnection: Connection = {
    val query: String = parameters.map(m => m._1 + "=" + m._2).mkString("&")
    val url: String = s"jdbc:mysql://$host:$port/$database?$query"
    Class.forName(driverName)
    DriverManager.getConnection(url, username, password)
  }

  private[this] def getLastOffset: Long ={
    val pt = conn.prepareStatement(s"SELECT COUNT(1) FROM $tableName")
    val rs = pt.executeQuery()
    var offset = 0L
    if (rs.next()) {
      offset = rs.getLong(1)
    }
    offset
  }

  private def parseOffset(json: String): Long = {
    implicit val formats: AnyRef with Formats = Serialization.formats(NoTypeHints)
    val map = Serialization.read[Map[String, Long]](json)
    map(tableName)
  }

  private def createDataFrame(buffer: Seq[InternalRow], schema: StructType): DataFrame = {
    val rdd = sqlContext.sparkContext.parallelize(buffer)
    sqlContext.internalCreateDataFrame(rdd, schema, isStreaming = true)
  }

  override def schema: StructType = schemaOption.get

  override def getOffset: Option[Offset] = {
    val offset = getLastOffset
    batchSize match {
      case None => Option(MySQLOffset(tableName, offset))
      case Some(limit) =>
        if (currentOffset + limit > offset) {
          Option(MySQLOffset(tableName, offset))
        } else {
          Option(MySQLOffset(tableName, currentOffset + limit))
        }
    }
  }

  override def getBatch(start: Option[Offset], end: Offset): DataFrame = {

    val startOffset = if (start.isDefined) parseOffset(start.get.json) else 0L
    val endOffset = parseOffset(end.json)

    val limit = endOffset - startOffset
    val fields = schema.fields.map(f => f.name).mkString(",")
    val sql = s"SELECT $fields FROM $tableName LIMIT $limit OFFSET $startOffset"
    //
    val pt = conn.prepareStatement(sql)
    val rs = pt.executeQuery()
    val rows = new ArrayBuffer[InternalRow]
    val meta = rs.getMetaData

    while (rs.next) {
      val row = new GenericInternalRow(meta.getColumnCount)
      for (i <- 0 until meta.getColumnCount) {
        //values += rs.getObject(i)
        meta.getColumnType(i + 1) match {
          case Types.VARCHAR => row.update(i, UTF8String.fromString(rs.getString(i + 1)))
          case Types.INTEGER => row.setInt(i, rs.getInt(i + 1))
        }
      }
      rows += row
    }

    // val rows = JdbcUtils.resultSetToSparkInternalRows(rs, schema, new InputMetrics())
    val dataframe = createDataFrame(rows.toSeq, schema)
    currentOffset = endOffset
    dataframe
  }

  override def stop(): Unit = {
    conn.close()
  }

  case class MySQLOffset(tableName: String, offset: Long) extends Offset {

    def getOffset: Long = offset

    override def json(): String = "{\"" + tableName + "\":" + offset + "}"
  }
}
