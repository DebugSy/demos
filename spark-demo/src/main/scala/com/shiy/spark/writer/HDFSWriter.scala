package com.shiy.spark.writer


import org.apache.spark.sql.{DataFrame, SparkSession}
import org.slf4j.{Logger, LoggerFactory}

/**
  * Created by DebugSy on 2017/9/30.
  */
class HDFSWriter() {

  val LOG: Logger = LoggerFactory.getLogger(classOf[HDFSWriter])

  def write(dataFrame: DataFrame, path: String): Unit ={
    LOG.info("show write data frame 10 lines")
    dataFrame.show(10)
    val writer = dataFrame.write
    writer.format("csv")
    writer.mode("append")
//    writer.save("spark-demo/src/main/resources/data_write.csv")
    writer.save(path)
  }
  
}

object HDFSWriter{
  def apply(): HDFSWriter = new HDFSWriter()
}