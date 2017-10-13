package com.shiy.spark.transform

import com.shiy.spark.basic.Step
import org.apache.spark.sql.DataFrame

/**
  * Created by DebugSy on 2017/10/10.
  */
class FilterStep extends Step{
  override var inputData: DataFrame = _
  override var outputData: DataFrame = _

  var condition = ""

//  var column = _

  override def input(dataFrame: DataFrame): Unit = {
    inputData = dataFrame
  }

  override def process(): DataFrame = {
    val dataset = inputData.filter(condition)
    return dataset
  }

//  def processColumn(): DataFrame = {
//    val dataset = inputData.filter(inputData("id") > 10)
//  }

  override def destroy(): Unit = {

  }

}
