package com.shiy.spark.demo

import org.apache.spark.{SparkConf, SparkContext}

object UrlCount {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("UrlCount").setMaster("local")
    val sc = new SparkContext(sparkConf)

    val urlData = sc.textFile("D:/tmp/data/spark/test_data/itcast.log")
    val result = urlData.map(_.split("\t")).map(x => (x(1).substring(7, x(1).indexOf('.')), 1)).reduceByKey(_ + _)
    result.collect().map(print(_))
    sc.stop()

  }

}
