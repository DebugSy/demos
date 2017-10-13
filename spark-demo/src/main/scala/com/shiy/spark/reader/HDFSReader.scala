package com.shiy.spark.reader

import org.apache.commons.lang.StringEscapeUtils
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.slf4j.{Logger, LoggerFactory}

/**
  * Created by DebugSy on 2017/9/29.
  */
class HDFSReader(master: String) {

  val LOG: Logger = LoggerFactory.getLogger(classOf[HDFSReader])

  val appName = "HDFS Reader"

  def read(path: String, schema: StructType) = {
    val sparkSession = SparkSession.builder().appName(appName).master(master).getOrCreate()
    val reader = sparkSession.read
    reader.format("com.databricks.spark.csv").options(
      Map("delimiter" -> StringEscapeUtils.unescapeJava(","),
        "quote" -> StringEscapeUtils.unescapeJava("\""),
        "escape" -> StringEscapeUtils.unescapeJava("\\")))
    reader.schema(schema)

    val dataFrame = reader.csv(path)
    dataFrame
  }

}

object HDFSReader {

  def apply(master: String): HDFSReader = {
    new HDFSReader(master)
  }

  def main(args: Array[String]): Unit = {


    val sparkSession = SparkSession.builder().appName("hdfs reader hdfs").master("local").getOrCreate()
    val sparkContext = sparkSession.sparkContext
    val reader = sparkSession.read
    val schema = StructType(
      StructField("id", IntegerType, true) ::
        StructField("name", StringType, true) ::
        StructField("age", IntegerType, true) :: Nil)
    reader.schema(schema)
    val path = "spark-demo/src/main/resources/data.csv";

    val dataFrame = reader.csv(path)
    dataFrame.schema
    dataFrame.show(10)

  }

}
