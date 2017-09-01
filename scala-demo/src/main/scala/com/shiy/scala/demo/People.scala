package com.shiy.scala.demo

/**
  * Created by DebugSy on 2017/9/1.
  */
abstract class People {

  def speak(s: String): String = {
    s"people speak $s"
  }

  def learn()

}
