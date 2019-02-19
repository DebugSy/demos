package com.shiy.spark.demo

import java.net.URI

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.catalyst.analysis.{Analyzer, FunctionRegistry}
import org.apache.spark.sql.catalyst.catalog.{CatalogDatabase, CatalogTable, SessionCatalog}
import org.apache.spark.sql.catalyst.plans.logical.Range
import org.apache.spark.sql.execution.SparkSqlParser
import org.apache.spark.sql.internal.SQLConf
import org.apache.spark.sql.types.{DataTypes, StructField, StructType}

/**
  * Created by DebugSy on 2019/2/19.
  */
object SqlSourceTest {

  def main(args: Array[String]): Unit = {
    val sc = SparkSession.builder().appName("SqlSourceTest").master("local[*]").getOrCreate()

    val df = sc.emptyDataFrame

    val schema = StructType(
      StructField("id", DataTypes.StringType, false) ::
        StructField("name", DataTypes.StringType, false) ::
        StructField("age", DataTypes.IntegerType, false) :: Nil
    )
    val data = sc.createDataFrame(df.rdd, schema)

//    data.createOrReplaceTempView("shiy_student_dataset")
    val sql = "select * from shiy_student_dataset where age > 20"

    val conf = new SQLConf
    val parser = new SparkSqlParser(conf)
    val externalCatalog = new TempMemoryCatalog(new SparkConf())
    val catalog = new SessionCatalog(externalCatalog, FunctionRegistry.builtin, conf)
    catalog.createTempView("shiy_student_dataset", data.queryExecution.logical, false)
    val analyzer = new Analyzer(catalog, conf)
    val plan = analyzer.execute(parser.parsePlan(sql))
    analyzer.checkAnalysis(plan)
    sc.stop()
  }

}
