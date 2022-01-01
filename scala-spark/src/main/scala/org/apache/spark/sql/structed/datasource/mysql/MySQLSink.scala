package org.apache.spark.sql.structed.datasource.mysql

import org.apache.spark.internal.Logging
import org.apache.spark.sql.execution.streaming.Sink
import org.apache.spark.sql.streaming.OutputMode
import org.apache.spark.sql.{DataFrame, SQLContext, SaveMode}

class MySQLSink(sqlContext: SQLContext,
                options: Map[String, String],
                outputMode: OutputMode) extends Sink
  with Logging {

  private[this] var lastBatchId: Long = 0L

  override def addBatch(batchId: Long, data: DataFrame): Unit =  {
    if (lastBatchId == batchId) {
      logInfo(s"Skipping already committed batch $batchId")
    } else {
      val rdd = data.queryExecution.toRdd
      sqlContext.internalCreateDataFrame(rdd, data.schema)
        .write
        .format("jdbc")
        .options(options)
        .mode(SaveMode.Append)
        .save()
      lastBatchId = batchId
    }

  }

}
