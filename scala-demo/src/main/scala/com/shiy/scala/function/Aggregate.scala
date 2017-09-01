package com.shiy.scala.function

/**
  * Created by DebugSy on 2017/8/30.
  */
object Aggregate {

  def add(x: Int, y: Int): Int ={
    val res = x * y
    println(s"add: $x * $y = $res")
    res
  }

  def addForList(x: Int, y: Int): Int ={
    val res = x + y
    println(s"addForList: $x + $y = $res")
    res
  }

  def listAdd(x: List[Int]): Int ={
    val res = x.foldLeft(1)(addForList _)
    println(s"list add res : $res")
    res
  }

  def main(args: Array[String]): Unit = {
    val z = List(List(1,2,3,4,5),List(6,7,8,9,10),List(1,2,3,4,5,6))
    val aggregate = z.par.aggregate(0)(_+listAdd(_) ,add(_,_))
    println(aggregate)
  }

}
