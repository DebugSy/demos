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

  def fib(n: Int): Int = {//斐波那契数列

    def fibs(m: Int, n: Int,limit: Int): Int = {
      var r = m + n
      if (limit == 1 ){
        r
      } else {
        fibs(n, r, limit - 1)
      }
    }

    if (n == 1)
      0
    else if (n == 2)
      1
    else
      fibs(0, 1, n-2)//尾递归
  }

  def main(args: Array[String]): Unit = {
    println(fib(8))
  }

}
