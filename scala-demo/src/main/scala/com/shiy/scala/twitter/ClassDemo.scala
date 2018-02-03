package com.shiy.scala.twitter

/**
  * Created by DebugSy on 2018/2/2.
  */
class ClassDemo(m: Int, n: Int) extends ClassCommon{

  val result = m + n

  if (result == 2){
    println("result is two.")
  } else if (result == 3){
    println("result is three")
  } else {
    println("result is other")
  }

  def addOne(): Int ={
    result * 100
  }

  override def sleep(): Unit = {
    println("sleeping .....")
  }
}

object ClassDemo{

  def main(args: Array[String]): Unit = {
    val cd = new ClassDemo(1,1)
    val result = cd.addOne()
    println(result)
    cd.sleep()
  }

}
