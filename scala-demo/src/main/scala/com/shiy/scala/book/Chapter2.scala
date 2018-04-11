package com.shiy.scala.book

/**
  * Created by DebugSy on 2018/4/10.
  */
object Chapter2 {

  def abs(n: Int): Int =
    if(n < 0)
      -n
    else n

  private def formatAbs(n: Int) = {
    val msg = "The absolute value of %d is %d"
    msg.format(n, abs(n))
  }

  def main(args: Array[String]): Unit = {
    println(formatAbs(-42))
  }

}
