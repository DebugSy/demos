package com.shiy.scala.demo

/**
  * Created by DebugSy on 2017/9/1.
  */
object OptionDemo {

  def main(args: Array[String]): Unit = {
    val map = Map("a" -> 1, "b" -> 2)
    def result(s: String): Int = map.get(s) match {
      case Some(i) => i//
      case None => 0
    }
    println(result("a"))
    println(result("b"))
    println(result("c"))

    val v = map.getOrElse("a", 0)
    println(v)
  }

}
