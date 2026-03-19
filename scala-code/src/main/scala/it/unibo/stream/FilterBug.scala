package it.unibo.stream

@main def testFilter(): Unit =
  import Streams.*
  import Stream.*
  def x1 = {
    println("computing x1...")
    1
  }
  def x2 = {
    println("computing x2...")
    2
  }
  // The problem:
  val s1 = cons(x1, cons(x2, empty()))
  println("before filtering...")
  val filtered = s1.filter(_ == 2) // `x1` and `x2` will not be computed!
  println("after filtering...")
  print(filtered.toList)
  // Even worse:
  val naturals = iterate(0)(_ + 1)
  naturals.filter(_ => false) // Loops!
