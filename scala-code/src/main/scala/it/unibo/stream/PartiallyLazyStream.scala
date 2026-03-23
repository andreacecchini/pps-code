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

  def interleave[A](s1: Stream[A], s2: Stream[A]): Stream[A] = s1 match
    case Cons(h1, t1) => cons(h1(), interleave(s2, t1()))
    case _ => s2

  extension [A](str: Stream[A])
    def toSequence: Sequence[A] = str match
      case Empty() => Sequence.Nil()
      case Cons(h, t) => Sequence.Cons(h(), t().toSequence)

    def take(n: Int): Stream[A] = str match
      case Cons(h, t) if n > 0 => cons(h(), t().take(n - 1))
      case _ => empty()

    def takeWhile(pred: A => Boolean): Stream[A] = str match
      case Cons(h, t) if pred(h()) => cons(h(), t().takeWhile(pred))
      case _ => empty()

    def map[B](mapper: A => B): Stream[B] = str match
      case Cons(h, t) => cons(mapper(h()), t().map(mapper))
      case _ => empty()

    def filter(pred: A => Boolean): Stream[A] = str match
      case Cons(h, t) if pred(h()) => cons(h(), t().filter(pred))
      case Cons(h, t) => t().filter(pred)
      case _ => Empty()

@main def testStream(): Unit =
  val streamImpl: StreamADT = PartiallyLazyStream
  import streamImpl.*

  def x1 = {println("computed x1..."); 1}
  def x2 = {println("computed x2..."); 2}
  val str = cons(x1, cons(x2, empty()))
  str.filter(_ => false) // will compute x1 and x2!
