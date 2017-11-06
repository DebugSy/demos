package com.shiy.spark

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

/**
  * Created by DebugSy on 2017/9/29.
  */
class SparkJob(appName: String, master: String) {

  val conf = new SparkConf().setAppName(appName)
  val builder = SparkSession.builder().config(conf).master(master)
  val sparkSession = builder.getOrCreate()

  def get(): SparkSession ={
    return sparkSession
  }

}

object SparkJob {
  def apply(appName: String, master: String): SparkJob = {
    new SparkJob(appName, master)
  }
}
