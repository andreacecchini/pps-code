package it.unibo.stream

import Streams.*

/** A partially lazy [[Stream]] implementation. */
object PartiallyLazyStream extends StreamADT:
  opaque type Stream[A] = Step[A]

  protected def fromStep[A](step: => Step[A]): Stream[A] = step

  extension [A](str: Stream[A])
    protected def step: Step[A] = str

@main def testStream(): Unit =
  val streamImpl: StreamADT = PartiallyLazyStream
  import streamImpl.*

  def x1 = {
    println("computed x1...");
    1
  }

  def x2 = {
    println("computed x2...");
    2
  }

  val str = cons(x1, cons(x2, empty()))
  str.filter(_ => false) // will compute x1 and x2!
