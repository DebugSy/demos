package com.shiy.spark.demo

import java.text.SimpleDateFormat

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by DebugSy on 2018/2/28.
  */
object Position {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("userLocation").setMaster("local")
    val sc = new SparkContext(sparkConf)
    val locData = sc.textFile("E:/tmp/spark/data/loc_info.txt").map(_.split(",")).map(x => (x(0), (x(1), x(2))))

    val sdf = new SimpleDateFormat("yyyyMMddHHmmss")
    val data = sc.textFile("E:/tmp/spark/data/log/*").map(_.split(",")).map(x => {
      (x(0) + "_" + x(2), sdf.parse(x(1)).getTime, x(3))
    }).groupBy(_._1).mapValues(_.map(
      x => if (x._3.toInt == 0) x._2.toLong else -x._2.toLong
    )).mapValues(_.sum).groupBy(_._1.split("_")(0)).map { case (k, v) => {
      //分组后二次排序
      (k, v.toList.sortBy(_._2).reverse(0))
    }
    }.map(t => (t._1, t._2._1.split("_")(1), t._2._2))

    data.map(t => (t._2, (t._1, t._3))).join(locData).map(x => (x._2._1._1, x._1, x._2._2._1, x._2._2._2)).saveAsTextFile("E:/tmp/spark/data/result")

    sc.stop()



  }

}
