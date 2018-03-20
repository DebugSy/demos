package com.shiy.scala.demo

import scala.collection.immutable.HashMap

/**
  * Created by DebugSy on 2017/9/29.
  */
object SomeDemo {

  def main(args: Array[String]): Unit = {

    val map = Map("a" -> "1","b" -> "2","c" -> "3")

    val isMap = map.isInstanceOf[Map[String, String]]

    println(isMap)



  }

}
