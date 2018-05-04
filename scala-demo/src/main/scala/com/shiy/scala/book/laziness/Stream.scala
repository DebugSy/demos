package com.shiy.scala.book.laziness

/**
  * Created by DebugSy on 2018/4/28.
  */
import Stream._
sealed trait Stream[+A]{
  def headOption: Option[A] = this match {
    case Empty => None
    case Cons(h,t) => Some(h())
  }

  //练习5.1
  def toList: List[A] = {
    @annotation.tailrec
    def go(s: Stream[A], acc: List[A]): List[A] = s match {
      case Cons(h,t) => go(t(), h() :: acc)
      case _ => acc
    }
    go(this, List()).reverse
  }

  //练习5.2
  def take(n: Int): Stream[A] = this match {
    case Cons(h, t) if (n > 0) => cons(h(), t().take(n - 1))
    case Cons(h, _) if (n == 1) => cons(h(), empty)
    case _ => empty
  }

  @annotation.tailrec
  final def drop(n: Int): Stream[A] = this match {
    case Cons(_, t) if (n > 0) => t().drop(n - 1)
    case _ => this
  }

  //练习5.3
  def takeWhile(p: A => Boolean): Stream[A] = this match {
    case Cons(h, t) if p(h()) => cons(h(), t() takeWhile p)
    case _ => empty
  }

  def exists(p: A => Boolean): Boolean = this match {
    case Cons(h, t) => p(h()) || t().exists(p)
    case _ => false
  }

  def exists2(p: A => Boolean): Boolean = {
    foldRight(false)( (a, b) => p(a) || b)
  }

  //f参数的类型声明中B类型前面的箭头表示第二个参数是传名参数，f不会对它进行求值
  def foldRight[B](z: => B)(f: (A, => B) => B): B = this match {
    case Cons(h, t) => f(h(), t().foldRight(z)(f))
    case _ => z
  }

  //练习5.4
  def forAll(p: A => Boolean): Boolean = {
    foldRight(true)((a, b) => p(a) && b)
  }

  //练习5.5
  def takeWhile2(p: A => Boolean): Stream[A] = {
    foldRight(empty[A])((h, t) => if (p(h)) cons(h, t) else empty)
  }

  //练习5.6
  def headOption2: Option[A] = {
    foldRight(None: Option[A])((h, _) => Some(h))
  }

  //练习5.7
  //练习5.8
  //练习5.9
  //练习5.10
  //练习5.11
  //练习5.12
  //练习5.13
  //练习5.14
  //练习5.15
  //练习5.16

}
case object Empty extends Stream[Nothing]
case class Cons[+A](h: () => A, t: () => Stream[A]) extends Stream[A]

object Stream {

  def cons[A](hd: => A, tl: => Stream[A]): Stream[A] = {
    lazy val head = hd
    lazy val tail = tl
    Cons(() => head, () => tail)
  }

  def empty[A]: Stream[A] = Empty

  def apply[A](as: A*): Stream[A] = {
    if (as.isEmpty) empty else cons(as.head, apply(as.tail: _*))
  }

  def main(args: Array[String]): Unit = {
    val list = Stream(1,2,3).take(2).toList
    println(list)
  }
}
