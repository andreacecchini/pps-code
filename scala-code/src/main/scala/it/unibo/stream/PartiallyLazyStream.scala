package it.unibo.stream

import it.unibo.algebraicDataType.Sequences.*
import Streams.*

/** A partially lazy [[Stream]] implementation. */
object PartiallyLazyStream extends StreamADT:
  opaque type Stream[A] = StreamImpl[A]

  private enum StreamImpl[A]:
    case Empty()
    case Cons(head: () => A, tail: () => StreamImpl[A])

  import StreamImpl.*

  def empty[A](): Stream[A] = Empty()

  def cons[A](hd: => A, tl: => Stream[A]): Stream[A] =
    lazy val head = hd
    lazy val tail = tl
    Cons(() => head, () => tail)

  def unfold[A, B](str: Stream[A])(
    onEmpty: => Stream[B],
    onCons: (=> A, => Stream[A]) => Stream[B]
  ): Stream[B] = str match
    case Empty() => onEmpty
    case Cons(h, t) => onCons(h(), t())

  def fold[A, B](str: Stream[A])(
    onEmpty: => B,
    onCons: (=> A, => Stream[A]) => B
  ): B = str match
    case Empty() => onEmpty
    case Cons(h, t) => onCons(h(), t())

@main def testStream(): Unit =
  val streamImpl: StreamADT = PartiallyLazyStream
  import streamImpl.*

  def x1 = {println("computed x1..."); 1}
  def x2 = {println("computed x2..."); 2}
  val str = cons(x1, cons(x2, empty()))
  str.filter(_ => false) // will compute x1 and x2!
