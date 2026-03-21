package it.unibo.stream

@main def testFilter(): Unit =
  import StreamBasics.*
  import Stream.*

  def x1 = {
    println("computing x1...")
    1
  }
  def x2 = {
    println("computing x2...")
    2
  }
  // A problem:
  val s1: Stream[Int] = cons(x1, cons(x2, empty()))
  println("before filtering...")
  val filtered = s1.filter(_ == 2) // `x1` and `x2` will be computed!
  println("after filtering...")
  print(filtered.toList) // computation should start here!
  // The problem:
  val naturals: Stream[Int] = iterate(0)(_ + 1)
  naturals.filter(_ => false) // Loops!
