package com.shiy.spark.demo

import org.apache.spark.sql.SparkSession

/**
  * Created by DebugSy on 2018/3/20.
  */
object SparkSessionDemo {

  def main(args: Array[String]): Unit = {

    val sparkSession = SparkSession.builder().master("local").appName("SparkSession Demo").enableHiveSupport().getOrCreate()


  }

}
