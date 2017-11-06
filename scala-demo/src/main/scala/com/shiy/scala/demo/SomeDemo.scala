package com.shiy.scala.demo

/**
  * Created by DebugSy on 2017/9/29.
  */
object SomeDemo {

  def main(args: Array[String]): Unit = {

    val map = Map("a" -> "1","b" -> "2","c" -> "3")

    val name: Option[String] = map.get("a")

    val matchResult = name match {
      case Some(name) => {
        println(name)
      }
      case None => {
        println("None")
      }
    }




  }

}
