package it.unibo.stream

import Streams.*

/** A real lazy [[Stream]] implementation. */
object LazyStream extends StreamADT:
  opaque type Stream[A] = () => Step[A]

  protected def fromStep[A](step: => Step[A]): Stream[A] = () => step

  extension [A](str: Stream[A])
    protected def step: Step[A] = str()

@main def testLazyStream(): Unit =
  val streamImpl: StreamADT = LazyStream
  import streamImpl.*

  val naturals = iterate(0)(_ + 1)
  println:
    naturals
      .filter(_ % 2 == 0)
      .map(_ + 1)
      .take(10)
      .toSequence


