package com.shiy.scala

import scala.collection.mutable.ArrayBuffer

object ScalaDemo1 {

  val fun2 = (x: Int, y: Int) => {
    (y, x)
  }

  val fun3: (Int, Double) => (Double, Int) = {
    (x, y) => (y, x)
  }

  def m1(f: Int => Int): Int ={
    f(3)
  }

  val  fun = (x: Int) => {
    x * 10
  }

  def m2(x: Int, y: Int) = x + y

  val fun4 = m2 _

  def main(args: Array[String]): Unit = {
    val list = List.fill(5)((1,2))
    val unzip = list.unzip
    println(list)
    println(unzip)
  }
}
