package com.shiy.spark.basic

import org.apache.spark.sql.DataFrame

/**
  * Created by DebugSy on 2017/10/10.
  */
abstract class Step {

  var inputData: DataFrame

  var outputData: DataFrame

  def input(dataFrame: DataFrame)

  def process(): DataFrame

  def destroy()

}
