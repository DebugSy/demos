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
//    val arra = for (i <- 1.to(10)) yield i * 10
//    for (i <- 0 until arra.length){
//      println(i)
//    }
//    val r = m1(fun)
//    println(r)
//
//    val arr = Array(1,2,3,4,5,6,7,8,9)
//    val  r1 = arr.map(x => x * 5)
//    val  r2 = arr.map(x => x - 1)
//    println(r1.toBuffer)
//    println(r2.toBuffer)

//    println(fun4(1, 2))

//    val arr = Array(1, 2, 3, 4, 5, 6)
//    println(arr.toBuffer)

//    val ab = ArrayBuffer[Int]()
//    ab += 2
//    ab += 3
//    ab += (1,2)
//    ab ++= Array(4,5)
//    println(ab.toBuffer)
//    println(ab.reverse.toBuffer)
//    for (i <- 0 until ab.length ) print(ab(i))

//    val a = Array(1, 2, 3, 4, 5)
//    val b1 = a.map(x => x * 10)
//    val b2 = a.map(_ * 10)
//    val c = for (i <- a) yield  i * 10
//    println(b1.toBuffer)
//    println(b2.toBuffer)
//    println(c.toBuffer)

//    val a = Array(1,2,3,4,5,6,7,8,9)
//    val b = for (i <- a if i % 2 == 0) yield i
//    println(b.toBuffer)
//    val c = a.filter(_ % 2 == 0)
//    println(c.toBuffer)

//    val a = Map("a" -> 1, "b" -> 2)
//    val b = scala.collection.mutable.Map("a" -> 1, "b" -> 2)
//    println(a("a"))
//    b("a") = 4
//    println(b("a"))

    val a = Array(1,2,3,4,5)
    val b = Array("a","b","c","d","e")
    val c = b.zip(a).toMap
    println(c)
  }
}
