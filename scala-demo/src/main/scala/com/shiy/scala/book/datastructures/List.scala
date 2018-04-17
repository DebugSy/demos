package com.shiy.scala.book.datastructures

/**
  * Created by DebugSy on 2018/4/13.
  */

sealed trait List[+A]
case object Nil extends List[Nothing]
case class Cons[+A](head: A, tail: List[A]) extends List[A]

object List {

  def sum(ints: List[Int]): Int = ints match {
    case Nil => 0
    case Cons(x, xs) => x + sum(xs)
  }

  def product(ds: List[Double]): Double = ds match {
    case Nil => 0.0
    case Cons(x, xs) => x * product(xs)
  }

  def foldRight[A,B](as: List[A], z: B)(f: (A,B) => B): B = {
    as match {
      case Nil => z
      case Cons(x, xs) => f(x, foldRight(xs, z)(f ))
    }
  }

  @annotation.tailrec
  def foldLeft[A,B](as: List[A], z: B)(f: (B,A) => B): B = {
    as match {
      case Nil => z
      case Cons(x, xs) => foldLeft(xs, f(z, x))(f)
    }
  }

  def sum2(ns: List[Int]) = foldRight(ns, 0)((x, y) => x + y)

  def product2(ns: List[Double]) = foldRight(ns, 1.0)(_ * _)// * _ *是(x,y)=>x*y的缩写

  def sum3(ns: List[Int]) = foldLeft(ns, 0)(_ + _)

  def product3(ns: List[Double]) = foldLeft(ns, 1.0)(_ * _)

  def length[A](l: List[A]): Int = {
    foldRight(l, 0)((_, acc) => {println(s"acc:$acc");acc + 1})
  }

  def length2[A](l: List[A]): Int = {
    foldLeft(l, 0)((acc, _) => acc + 1)
  }

  def apply[A](as: A*): List[A] = //可变参数
    if (as.isEmpty) Nil
    else Cons(as.head, apply(as.tail: _*))

  val x= List(1,2,3,4,5) match {
    case Cons(x, Cons(2, Cons(4, _))) => x
    case Nil => 42
    case Cons(x, Cons(y, Cons(3, Cons(4, _)))) => x + y
    case _ => 101
  }

  def tail[A](l: List[A]): List[A] = {
    l match {
      case Nil => sys.error("tail of empty list")
      case Cons(_, t) => t
    }
  }

  def setHead[A](l: List[A], h: A): List[A] = {
    l match {
      case Nil => sys.error("setHead on empty list")
      case Cons(_, t) => Cons(h, t)
    }
  }

  def drop[A](l: List[A], n: Int): List[A] = {
    if (n <= 0) l
    else l match {
      case Nil => Nil
      case Cons(_, t) => drop(t, n-1)
    }
  }

  /**
    * 调用：
    * val l: List[Int] = List(1,2,3,4,5)
    * val r = dropWhile(l, (x: Int) => x < 3) //必须带类型Int
    */
  def dropWhile[A](l: List[A], f: A => Boolean): List[A] = {
    l match {
      case Cons(h, t) if f(h) => dropWhile(t, f)
      case _ => l
    }
  }


  /**
    * 调用:
    * val l: List[Int] = List(1,2,3,4,5)
    * val r = dropWhile2(l)(x => x < 3)     //柯里化能最大化利用类型推导，不用在带类型Int
    */
  def dropWhile2[A](l: List[A])(f: A => Boolean): List[A] = {//柯里化 => 最大化地利用类型推导
    l match {
      case Cons(h, t) if f(h) => dropWhile2(t)(f)
      case _ => l
    }
  }

  def append[A](l1: List[A], l2: List[A]): List[A] = {
    l1 match {
      case Nil => l2
      case Cons(h, t) => Cons(h, append(t, l2))
    }
  }

  def main(args: Array[String]): Unit = {
    val list = List(1,2,3,4)
    val len = length(list)
    println(len)
  }

}
