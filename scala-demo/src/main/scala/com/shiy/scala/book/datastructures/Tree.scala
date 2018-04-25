package com.shiy.scala.book.datastructures

/**
  * Created by DebugSy on 2018/4/25.
  */
sealed trait Tree[+A]
case object tNil extends Tree[Nothing]
case class Leaf[A](value: A) extends Tree[A]
case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]
object Tree {

  def apply[A](as: A*): Tree[A] = {
    if (as.isEmpty) tNil
    else Branch(as.head: Tree[A], apply(as.tail: _*))
  }

  //练习3.25
  def size[A](t: Tree[A]): Int = t match {
    case Leaf(_) => 1
    case Branch(l,r) => 1 + size(l) + size(r)
  }

  //练习3.26

  //练习3.27

  //练习3.28


  def main(args: Array[String]): Unit = {
    val t = Tree(1,2,3,4)
    println(t)
  }

}
