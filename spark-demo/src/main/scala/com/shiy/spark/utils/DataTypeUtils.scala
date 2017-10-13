package com.shiy.spark.utils

import org.apache.spark.sql.types.{DataTypes, StructField, StructType}

/**
  * Created by DebugSy on 2017/9/30.
  */
object DataTypeUtils {

  def createDataType(schemas: Map[String,String]): StructType ={
    val fields = schemas.map(schema => makeStructField(schema._1, schema._2, true))
    DataTypes.createStructType(fields.toArray)
  }

  private[this] def makeStructField(name: String, stype: String, nullable: Boolean ): StructField ={
    val filedType =  stype.toLowerCase match {
      case "int" => DataTypes.IntegerType
//      case "double" => DataTypes.DoubleType
      case "double" => DataTypes.createDecimalType(10,4)
      case "binary" => DataTypes.BinaryType
      case "boolean" => DataTypes.BooleanType
      case "date" => DataTypes.DateType
      case "float" => DataTypes.FloatType
      case "long" => DataTypes.LongType
      case "short" => DataTypes.ShortType
      case "timestamp" => DataTypes.TimestampType
      case "string" => DataTypes.StringType
      case "decimal" => DataTypes.createDecimalType(10,4)
      case "bigint" => DataTypes.createDecimalType(24,2)
      case _ => throw new IllegalArgumentException("argument error")
    }
    val structField = new StructField(name, filedType, nullable)
    structField
  }

}
