package com.shiy.spark

import org.apache.spark.sql.SparkSession

class SparkLearn {

}

object SparkLearn{

  def main(args: Array[String]): Unit = {

    val session = SparkSession.builder().appName("spark 2.2 demo").master("local").getOrCreate()

   val sparkContext = session.sparkContext

    val rdd = sparkContext.parallelize(List(1,2,3,4,5,6))

    val rdd1 = rdd.map( _ * 10)

    val array = rdd1.collect()

    println(array)






  }

}
