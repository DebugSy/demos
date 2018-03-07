package com.shiy.spark.demo

import java.net.URL

import org.apache.spark.{SparkConf, SparkContext}

object UrlCount {

  def main(args: Array[String]): Unit = {

    val subjects = Array("java.itcast.cn", "net.itcast.cn", "php.itcast.cn")

    val sparkConf = new SparkConf().setAppName("UrlCount").setMaster("local")
    val sc = new SparkContext(sparkConf)

    val urlData = sc.textFile("D:/tmp/data/spark/test_data/itcast.log")
    val rdd1 = urlData.map(_.split("\t")).map(x => (x(1), 1)).reduceByKey(_ + _)

    val rdd2 = rdd1.map(t => {
      val url = t._1
      val host = new URL(url).getHost
      (host, url, t._2)
    })
//    rdd2.collect().map(println(_))

    subjects.foreach(subject => {
      val rdd3 = rdd2.filter(_._1 == subject).sortBy(_._3, false).take(3)
      rdd3.map(println(_))
      println("***************************")
    })


    sc.stop()

  }

}
