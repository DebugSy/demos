package com.shiy.scala.demo

import java.io.File

import scala.io.Source

/**
  * Created by DebugSy on 2017/9/7.
  */

class RichFIle(val fromFile: File){
  def readFile = Source.fromFile(fromFile.getPath).mkString
}

object RichFIle{

  //隐式转换方法
  implicit def file2RichFile(fromFile: File) = new RichFIle(fromFile)

  //隐式转换类
  implicit class string2Int(s: String) {
    def sToInt = s.toInt
  }

  //隐式转换方法
  implicit def intToString(x: Int) = x.toString

}

object ImplicitDemo {

  def main(args: Array[String]): Unit = {
    //导入隐式转换
    import RichFIle.intToString
    import RichFIle.string2Int
    import RichFIle.file2RichFile


    println(new File("scala-demo\\src\\main\\resources\\com.shiy.scala\\data.csv").readFile)
    val result1 = foo1("3".sToInt)
    println(result1)

    val result2 = foo2(2)
    println(result2)
  }

  def foo1(x: Int): Int ={
    return x * x
  }

  def foo2(x: String): Int = {
    return x.toInt * x.toInt
  }

}


