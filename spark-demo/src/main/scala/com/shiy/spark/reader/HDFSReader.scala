package com.shiy.spark.reader

import com.shiy.spark.SparkJob

/**
  * Created by DebugSy on 2017/9/29.
  */
class HDFSReader {

  val appName = "HDFS Reader"
  var master =
  var sparkJob = _

  def read(path: String) = {
    val sparkJob = SparkJob(appName, master)
    sparkJob.sparkSession.
  }

}

object HDFSReader{

  def apply(master: String): HDFSReader = {
    new HDFSReader()
  }

}
