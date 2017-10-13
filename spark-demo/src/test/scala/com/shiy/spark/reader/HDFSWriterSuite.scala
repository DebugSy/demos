package com.shiy.spark.reader

import com.shiy.spark.utils.DataTypeUtils
import com.shiy.spark.writer.HDFSWriter
import org.scalatest.FunSuite


/**
  * Created by DebugSy on 2017/9/30.
  */
class HDFSWriterSuite extends FunSuite{

  test("Test HDFS Writer"){
    val dataTypeMap = Map("id" -> "int", "name" -> "string", "age" -> "int")
    val schema = DataTypeUtils.createDataType(dataTypeMap)
    val path = "E:/GitHub/demos/spark-demo/src/test/resources/data.csv"
    val outputPath = "E:/tmp/spark_demo/data_read_write"
    val dataFrame = HDFSReader("local").read(path, schema)
    HDFSWriter().write(dataFrame, outputPath)
  }

}
