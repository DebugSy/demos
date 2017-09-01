package com.shiy.scala.demo

/**
  * Created by DebugSy on 2017/9/1.
  */
object PartialFunctionDemo {

  def fun1: PartialFunction[String, Int] = {
    case "one" => 1
    case "two" => 2
    case _ => 0
  }

  def fun2(num: String): Int = num match {
    case "one" => 1
    case "two" => 2
    case _ => 0
  }

  def main(args: Array[String]): Unit = {
    println(fun1("one"))
    println(fun2("two"))
  }
}
