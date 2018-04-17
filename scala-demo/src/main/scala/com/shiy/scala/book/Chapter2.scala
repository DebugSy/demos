package com.shiy.scala.book

/**
  * Created by DebugSy on 2018/4/10.
  */
object Chapter2 {

  //2.1 - 2.4
  def abs(n: Int): Int =
    if(n < 0)
      -n
    else n

  def factorial(n: Int): Int = {

    def fac(n: Int, acc: Int): Int = {
      if (n <= 0 )
        acc
      else
        fac(n-1 , n * acc)
    }
    fac(n, 1) //尾递归,会被编译为迭代的循环，这样每次迭代时不消耗栈帧的调用开销
  }

  private def formatAbs(n: Int) = {//绝对值函数
    val msg = "The absolute value of %d is %d."
    msg.format(n, abs(n))
  }

  private def formatFactorial(n: Int) = {//阶乘函数
    val msg = "The factorial of %d is %d."
    msg.format(n, factorial(n))
  }

  def formatResult(name: String, n: Int, f: Int => Int ) = {//高阶函数HOF，接受一个函数作为参数
    val msg = "The %s of %d is %d."
    msg.format(name, n, f(n))
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

  //2.5
  def findFirst[A](as: Array[A], p: A => Boolean): Int = {
    @annotation.tailrec//如果递归不能编译为迭代会报错
    def loop(n: Int): Int = {
      if (n >= as.length) -1
      else if (p(as(n))) n
      else loop(n + 1)
    }
    loop(0)
  }

  //练习2.2
  def isSorted[A](as: Array[A], ordered: (A, A) => Boolean): Boolean = {
    @annotation.tailrec
    def sort(n: Int): Boolean = {
      if (n >= as.length - 1) false
      else if (ordered(as(n), as(n+1))) true
      else sort(n + 1)
    }
    sort(0)
  }

  def compare(m: Int, n: Int): Boolean = {
    if (m<n) true else false
  }



  def main(args: Array[String]): Unit = {
    val as = Array(1,2,3,4,5)
    println(isSorted(as, compare))
  }

}
