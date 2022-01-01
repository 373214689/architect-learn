package com.liuyang.learn

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Dataset, ForeachWriter, Row, SparkSession}
import org.apache.spark.sql.streaming.OutputMode
import org.apache.spark.sql.types.StructType
import org.slf4j.LoggerFactory

import java.util
import scala.collection.mutable.ArrayBuffer

/**
 * Hello world!
 *
 */
object StructedStreamingApp {
  val log = LoggerFactory.getLogger(this.getClass)
  def main(args:Array[String]) ={
    println( "Hello World!" )

    val spark = SparkSession.builder()
      .appName("StructStreamingDemo")
      .getOrCreate()

    val conf = spark.sparkContext.getConf

    val schema = StructType.fromDDL("name string, value string")

    val df = spark.readStream
      .format("org.apache.spark.sql.structed.datasource.mysql.MySQLSourceProvider")
      .schema(schema)
      .option("driverName", "com.mysql.cj.jdbc.Driver")
      .option("host", "localhost")
      .option("port", "3306")
      .option("username", "root")
      .option("password", "root")
      .option("database", "manager")
      .option("table", "test_streaming")
      .option("setUnicode", "true")
      .option("charsetEncoding", "utf-8")
      .load

    val query = df.writeStream
      .outputMode(OutputMode.Append())
      .option("checkpointLocation", "/tmp/checkpoint/mysql")
      .foreachBatch { (df, batchId) =>  // <-- creates a ForeachBatchSink
        println(s"Batch ID: $batchId")
        df.show
      }
      .start()

    query.awaitTermination()

    spark.stop()
  }

}
