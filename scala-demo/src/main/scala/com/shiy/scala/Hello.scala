package com.shiy.scala

import java.io.PrintWriter

import scala.io.Source

/**
  * Created by DebugSy on 2017/8/22.
  */
object Hello extends App{

  println("hello => " + args(0))

  //读文件
  val source = Source.fromFile("E:/tmp/data.txt")
  val lineIterator = source.getLines
  for (line <- lineIterator){
    println(line)
  }

  //写文件2
  val file = new PrintWriter("E:/tmp/data_output.txt")
  for (i <- 1 to 10) {
//    println(i)
    file.print(i)
  }
  file.close()

  //正则表达式
  val numPattern = "[0-9]+".r
  for (matchString <- numPattern.findPrefixOf("99 bottles, 98 bottles")){
    println(matchString)
  }

}
