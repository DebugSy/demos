package com.shiy.scala

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
  * Created by DebugSy on 2017/8/21.
  */
class DefinePackage {

  private val a = 10

  def print1(): Unit ={
    println(a)
  }

  def getSum(x: Int, y: Int): Int ={
    val sum = x + y
    sum
  }

  def isMatch(x: Int): Unit = {
    var a = 1
    x match {
      case 1 => println(1)
      case 2 => println(2)
      case 3 => println(3)
      case _ => println("other")
    }
  }

  def isMax(max: Int, x: Int): Unit ={
    if (max < x){
      println(max + " < " + x)
    } else if (max > x){
      println(s"${max} > ${x}")
    } else {
      println(s"${max} == ${x}")
    }
  }

  def unitCase{
    println("unit")
  }

}
object DefinePackage{

  def main(args: Array[String]): Unit = {
    val definePackage = new DefinePackage
    definePackage.print1
    val sum = definePackage.getSum(1, 2)
    println(sum)
    definePackage.isMatch(4)

    definePackage.isMax(100, 101)

    println("aaaaa".toUpperCase)

    println("123456"(4))//apply()方法

    val str: String = "123456"

//    val line = readLine()
//    println(line)

    val c = 1 to 10
    for (d <- c){//for循环
      print(d + "\t")
    }

    for (d <- 0 until c.length){//for循环
      print(d + "\t")
    }

    println()

    for (i <- 1 to 3;j <- 1 to 3 if i != j){//for循环
      println(i * j)
    }

    definePackage.unitCase

    val array = new Array[String](10)//Array定长数组
    val array1 = Array("c", "d")
    array(0) = "a"
    array(1) = "b"
    println(array(1))
    println(array1(1))

    val arrayBuffer = new ArrayBuffer[String]()//不定长数组
    arrayBuffer += "a"
    arrayBuffer += "b"
    println(arrayBuffer(1))
    arrayBuffer.insert(2, "c")
    println(arrayBuffer(2))

    //map集合
    val scores_unmodify = Map("Alice" -> 10, "Bob" -> 3, "Condy" -> 8)//不可变的map集合
    val scores_modify = scala.collection.Map("Alice" -> 10, "Bob" -> 3, "Condy" -> 8)//可变的map集合
    val scores_null = new mutable.HashMap[String, Int]()
    println(scores_unmodify("Alice"))//获取map值
    println(scores_unmodify.getOrElse("Bob", 0))//获取map值并给定默认值

    //更新map
//    scores_modify("Alice") = 20
    println(scores_modify("Alice"))

    scores_null("Alice") = 30
  }
}
