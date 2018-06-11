package com.shiy.scala.book

/**
  * Created by DebugSy on 2018/5/4.
  */
object Chapter6 {

  def rollDie: Int = {
    val rng = scala.util.Random
    rng.nextInt(6)
  }

  def main(args: Array[String]): Unit = {
    val n1 = rollDie
    val n2 = rollDie
    println(Int.MinValue)
    println(Int.MaxValue)
  }

}
