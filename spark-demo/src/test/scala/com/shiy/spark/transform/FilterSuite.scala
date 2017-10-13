package com.shiy.spark.transform

import com.shiy.spark.reader.HDFSReader
import com.shiy.spark.utils.DataTypeUtils
import com.shiy.spark.writer.HDFSWriter
import org.scalatest.FunSuite

/**
  * Created by DebugSy on 2017/10/10.
  */
class FilterSuite extends FunSuite{

  test("test filter"){

    //"stock_id","stock_name","stock_price","stock_change","stock_range","stock_amplitude","stock_trading_number","stock_trading_value","stock_yesterdayfinish_price","stock_todaystart_price","stock_max_price","stock_min_price","stock_fiveminuate_change","craw_time"
//    val dataTypeMap = Map("id" -> "int", "name" -> "string", "age" -> "int")
    //"stock_change" -> "double" ,"stock_range" -> "double" , "stock_amplitude" -> "double" , "stock_trading_number" -> "double", "stock_trading_value" -> "double" , "stock_yesterdayfinish_price" -> "double", "stock_todaystart_price" -> "double", "stock_max_price" -> "double", "stock_min_price" -> "double", "stock_fiveminuate_change" -> "double", "craw_time" -> "timestamp"
    val dataTypeMap = Map(
    "stock_id" -> "string",
    "stock_name" -> "string",
    "stock_price" -> "string",
    "stock_change" -> "string",
    "stock_range" -> "string",
    "stock_amplitude" -> "string",
    "stock_trading_number" -> "string",
    "stock_trading_value" -> "string",
    "stock_yesterdayfinish_price" -> "string",
    "stock_todaystart_price" -> "string",
    "stock_max_price" -> "string",
    "stock_min_price" -> "string",
    "stock_fiveminuate_change" -> "string",
    "craw_time" -> "string"
)

    val dataTypeMap1 = Map("stock_id" -> "string", "stock_name" -> "string")

    val schema = DataTypeUtils.createDataType(dataTypeMap)
    val path = "E:/tmp/data/stock.csv"
    val outputPath = "E:/tmp/spark_demo/data_read_write"
    val dataFrame = HDFSReader("local").read(path, schema)
    val filterStep = new FilterStep
    filterStep.condition = "stock_price <= 10"
    filterStep.input(dataFrame)
    val output = filterStep.process()
    HDFSWriter().write(output, outputPath)
  }

}
