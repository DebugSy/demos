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
    case Cons(h, t) if (p(h)) => cons(h(), t() takeWhile p)
    case _ => empty
  }

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
