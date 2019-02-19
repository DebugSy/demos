package com.shiy.spark.demo

import java.util

import org.apache.spark.SparkConf
import org.apache.spark.sql.catalyst.TableIdentifier
import org.apache.spark.sql.catalyst.catalog.{CatalogStorageFormat, CatalogTable, CatalogTableType, InMemoryCatalog}
import org.apache.spark.sql.types.DataTypes

/**
  * Created by DebugSy on 2019/2/19.
  */
class TempMemoryCatalog(conf: SparkConf) extends InMemoryCatalog(conf) {

  val tables = new util.LinkedHashSet[String]()

  override def getTable(db: String, table: String): CatalogTable = {

    tables.add(table)
    //    val sc = SparkSession.builder().config(conf).getOrCreate()
    //    val emptyDataFrame = sc.emptyDataFrame
    //    val tempDF = sc.createDataFrame(emptyDataFrame.rdd, structType)
    //    tempDF.createOrReplaceTempView(table)

    val schema = DataTypes.createStructType(
      Array(
        DataTypes.createStructField("id", DataTypes.StringType, false),
        DataTypes.createStructField("name", DataTypes.StringType, false),
        DataTypes.createStructField("age", DataTypes.IntegerType, false)
      )
    )
    new CatalogTable(TableIdentifier(table, Option("")), CatalogTableType.EXTERNAL, CatalogStorageFormat.empty, schema)
  }

  override def databaseExists(db: String): Boolean = true

  override def functionExists(db: String, funcName: String): Boolean = {
    false
  }
}
