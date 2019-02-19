package com.shiy.spark.demo

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

object StudentDemo {

  def main(args: Array[String]): Unit = {

    val sc = SparkSession.builder().master("local").appName("student demo").getOrCreate()

    val student: DataFrame = sc.sqlContext.read.json("D:/tmp/data/spark/maxiaoli_test/student.json")
    val score: DataFrame = sc.sqlContext.read.json("D:/tmp/data/spark/maxiaoli_test/score.json")

    student.printSchema()
    score.printSchema()

    val result = score.join(student, score("name") === student("name"))

    result.collect().map(println(_))


  }

}
