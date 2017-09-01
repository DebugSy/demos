package com.shiy.scala.demo

/**
  * Created by DebugSy on 2017/8/31.
  */
class Person(var name: String, age: Int) extends People with Animal{

  override def eat(): Unit = {
    println("person eat rich")
  }

  private var address: String = _

  override def run(): String = {
    address = "hu guang zhong lu"
    s"$name are running at $address"
  }

  override def learn(): Unit = {
    println("people learning...")
  }

  def see: String ={
    ""
  }
}

object Person{

  val p = new Person("zhangsan", 24)



  def doSport(): Unit ={
    println(s"${p.name} do sport")
  }

  def main(args: Array[String]): Unit = {
    doSport()
    p.eat()
    println(p.run())
    println(p.speak("hello world"))
    p.learn()

  }
}
