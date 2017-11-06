package com.shiy.scala.demo

/**
  * Created by DebugSy on 2017/9/7.
  */
object FunDemo {

  def main(args: Array[String]): Unit = {
    def f2(x: Int) = x * 2
    val f3 = (x: Int) => x * 3
    val f4: (Int) => Int = { x => x * 4 }
    val f4a: (Int) => Int = { _ * 4 }
    val f5 = (_ : Int) * 5

    val list = List(1,2,3,4,5)
    var new_list: List[Int] = null

    //第一种，最直观的方式
    new_list = list.map((x: Int) => x * 3)

    //第二种，由于map知道会传入一个类型为(Int) => Int的函数，所以可以简写
    new_list = list.map( (x) => x * 3)

    //第三种，对于只有一个参数的函数，可以省去参数外围的()
    new_list = list.map( x => x * 3)

    //第四种， （终极方式）如果参数在=>右侧只出现一次，可以使用_
    new_list = list.map( _ * 3)

    new_list.foreach(println)


  }

}
