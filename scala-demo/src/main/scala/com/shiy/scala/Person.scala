package com.shiy.scala

import java.io.IOException

/**
  * Created by DebugSy on 2017/8/22.
  */
class Person(name: String, age: Int){

  println("执行主构造器。。。")

  try{
    println("读取文件")
    throw new IOException("io exception")
  }catch {
    case e: NullPointerException => println("打印异常Exception : " + e)
    case e: IOException => println("打印异常Exception : " + e)
  }finally {
    println("执行finally部分")
  }

  private var gender = "male"

  def this(name: String, age: Int, gender: String){
    this(name, age)
    println("执行辅助构造器")
    this.gender = gender
  }

  def eat(food: String): Unit ={
    println(s"$name is $age years old $gender eating $food")
  }
}

object Person{
  def main(args: Array[String]): Unit = {
    val p = Person("zhangsan", 20, "female")
    p.eat("apple")
  }

  def apply(name: String, age: Int): Person = new Person(name, age)

  def apply(name: String, age: Int, gender: String): Person = new Person(name, age, gender)
}
