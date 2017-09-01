package com.shiy.scala.function

/**
  * Created by DebugSy on 2017/8/30.
  */
object TestFunctionOnly {

  def seqOP(a:Int, b:Int):Int = {
    println("seqOp: " + a + "\t" +b )
    math.min(a,b)
  }
  //seqOP : (a:Int, b:Int)Int

  def combOP(a:Int, b:Int):Int = {
    println("combOP: " + a + "\t" + b)
    a + b
  }
  //combOP:(a:Int,b:Int)Int

  def main(args: Array[String]): Unit = {
    val z = List(1,2,3,4,5,6)
    z.aggregate(3)(seqOP, combOP)
  }

}
