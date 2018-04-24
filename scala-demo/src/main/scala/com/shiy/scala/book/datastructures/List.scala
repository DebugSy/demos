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

  //练习3.10
  @annotation.tailrec
  def foldLeft[A,B](as: List[A], z: B)(f: (B,A) => B): B = {
    as match {
      case Nil => z
      case Cons(x, xs) => foldLeft(xs, f(z, x))(f)
    }
  }

  //练习3.9
  def sum2(ns: List[Int]) = foldRight(ns, 0)((x, y) => x + y)

  def product2(ns: List[Double]) = foldRight(ns, 1.0)(_ * _)// * _ *是(x,y)=>x*y的缩写

  //练习3.11
  def sum3(ns: List[Int]) = foldLeft(ns, 0)(_ + _)

  def product3(ns: List[Double]) = foldLeft(ns, 1.0)(_ * _)

  //练习3.9
  def length[A](l: List[A]): Int = {
    foldRight(l, 0)((_, acc) => {println(s"acc:$acc");acc + 1})
  }

  def length2[A](l: List[A]): Int = {
    foldLeft(l, 0)((acc, _) => acc + 1)
  }

  //练习3.12
  def reverse[A](l: List[A]): List[A] = {
    foldLeft(l, List[A]())( (A,B) => {println(s"$A - $B");Cons(B,A)})
  }

  //练习3.13
  def foldLeft2[A,B](as: List[A], z: B)(f: (B,A) => B): B = {
    foldRight(reverse(as), z)((B,A) => f(A,B))
  }

  def foldRightViaFoldLeft[A,B](as: List[A], z: B)(f: (A,B) => B): B = {
    foldLeft(reverse(as), z)((B,A) => f(A,B))
  }

  def foldRightViaFoldLeft_1[A,B](as: List[A], z: B)(f: (A,B) => B): B = {
    foldLeft(as, (b:B) => {println(s"wb:$b");b})((g,a) => b => g(f(a,b)))(z)
  }

  def foldLeftViaFoldRight[A,B](l: List[A], z: B)(f: (B,A) => B): B =
    foldRight(l, (b:B) => b)((a,g) => b => g(f(b,a)))(z)


  //练习3.5
  def append[A](l1: List[A], l2: List[A]): List[A] = {
    l1 match {
      case Nil => l2
      case Cons(h, t) => Cons(h, append(t, l2))
    }
  }

  //练习3.14
  def appendViaFoldRight[A](l: List[A], r: List[A]): List[A] = {
    foldRight(l, r)(Cons(_,_))
  }

  def appendViaFoldLeft[A](l: List[A], r: List[A]): List[A] = {
    foldLeft(reverse(l), r)((A,B) => Cons(B,A))
  }

  //练习3.15
  def concat[A](l: List[List[A]]): List[A] = {
    foldRight(l, Nil:List[A])(append)
  }

  //练习3.16
  def add1(l: List[Int]): List[Int] = {
    foldLeft(l, Nil:List[Int])((h,t) => Cons(t+1, h))
  }

  //练习3.17
  def double2String(l: List[Double]): List[String] = {
    foldLeft(l, Nil:List[String])((h,t) => Cons(t.toString, h))
  }

  //练习3.18
  def map[A,B](as: List[A])(f: A => B): List[B] = {
    foldRight(as, Nil: List[B])( (h,t) => Cons(f(h), t))
  }

  //练习3.19
  def filter[A](as: List[A])(f: A => Boolean): List[A] = {
    foldRight(as, Nil:List[A])((h,t) => if (f(h)) Cons(h, t) else t)
  }

  //练习3.20
  def flatMap[A,B](as: List[A])(f: A => List[B]): List[B] = {
    concat(map(as)(f))
  }

  //练习3.21

  //练习3.22

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

  def main(args: Array[String]): Unit = {
//    val r = foldRightViaFoldLeft_1(List(1,2,3,4), 0)((A,B) => {println(s"$A + $B");A + B})
////    val r = foldLeft(List(1,2,3,4), 0)((B,A) => {println(s"$A + $B");A + B})
//    println(r)

//    val l = List(1,2,3,4)
//    val r = List(5,6,7,8)
//    val as = appendViaFoldLeft(l, r)
//    println(as)

    val l = List(1.0,2,3,4)
    println(double2String(l))
  }

}
