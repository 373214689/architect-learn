package org.apache.spark.sql.structed.datasource.mysql

import org.apache.spark.internal.Logging
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.execution.streaming.{Sink, Source}
import org.apache.spark.sql.sources.{DataSourceRegister, StreamSinkProvider, StreamSourceProvider}
import org.apache.spark.sql.streaming.OutputMode
import org.apache.spark.sql.types.StructType

class MySQLSourceProvider extends StreamSourceProvider
  with StreamSinkProvider
  with DataSourceRegister
  with Logging {

  override def shortName(): String = "mysql"

  override def sourceSchema(sqlContext: SQLContext,
                            schema: Option[StructType],
                            providerName: String,
                            parameters: Map[String, String]): (String, StructType) = {
    (providerName, if (schema.isDefined) schema.get else null)
  }

  override def createSource(sqlContext: SQLContext,
                            metadataPath: String,
                            schema: Option[StructType],
                            providerName: String,
                            parameters: Map[String, String]): Source = {
    new MySQLSource(sqlContext, parameters, schema)
  }

  override def createSink(sqlContext: SQLContext,
                          parameters: Map[String, String],
                          partitionColumns: Seq[String],
                          outputMode: OutputMode): Sink = {
    new MySQLSink(sqlContext, parameters, outputMode)
  }
}
