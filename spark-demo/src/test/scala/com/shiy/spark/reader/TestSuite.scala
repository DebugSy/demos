package com.shiy.spark.reader

import org.scalatest.FunSuite

import scala.collection.immutable

/**
  * Created by DebugSy on 2017/9/30.
  */
class TestSuite extends FunSuite{

  test("test map"){
    val map = Map("a" -> 1,"b" -> 2,"c" -> 3)
    def fun(x: Int): Int = {
      x * x
    }

    def get(): immutable.Iterable[Int] ={
      map.map(x => fun(x._2))
    }

    println(get())
  }

}
