package com.shiy.spark.sql

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

/**
  * Created by DebugSy on 2018/9/21.
  */
object SqlExecutionPlan {

  case class Student(sid: Int, sname: String, sex: String, age: Int, dept: String)
  case class Course(cno: Int, cname: String)
  case class Score(sid: Int, cid:Int, grade: Int)

  val studentSchema = StructType(
    StructField("sid", IntegerType, false) ::
    StructField("sname", StringType, false) ::
    StructField("sex", StringType, false) ::
    StructField("age", IntegerType, false) ::
    StructField("dept", StringType, false) :: Nil
  )

  val courseSchema = StructType(
      StructField("cno", IntegerType, false) ::
      StructField("cname", StringType, false) :: Nil
  )

  val scSchema = StructType(
      StructField("sid", IntegerType, false) ::
      StructField("cid", IntegerType, false) ::
      StructField("grade", IntegerType, false) :: Nil
  )

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("sql execution plan").master("local").getOrCreate()

    //导入隐式转换
    import spark.implicits._

    val student = spark.read.csv("src/main/resources/sql/students.txt")
    val course = spark.read.csv("src/main/resources/sql/course.txt")
    val sc = spark.read.csv("src/main/resources/sql/sc.txt")

    val studentDF = spark.sparkContext
      .textFile("src/main/resources/sql/students.txt")
      .map(line => line.split(","))
      .map(s => Student(s(0).toInt, s(1), s(2), s(3).toInt, s(4))).toDF()

    val courseDF = spark.sparkContext
      .textFile("src/main/resources/sql/course.txt")
      .map(line => line.split(","))
      .map(c => Course(c(0).toInt, c(1)))
      .toDF()

    val scDF = spark.sparkContext
      .textFile("src/main/resources/sql/sc.txt")
      .map(_.split(","))
      .map(s => Score(s(0).toInt, s(1).toInt, s(2).toInt))
      .toDF()

    studentDF.createOrReplaceTempView("student")
    courseDF.createOrReplaceTempView("course")
    scDF.createOrReplaceTempView("sc")

    spark.sql("select * from (select s.sid,s.sname,sc.grade from student s join sc on s.sid = sc.sid) z where z.grade > 80").explain(true)

  }

}
