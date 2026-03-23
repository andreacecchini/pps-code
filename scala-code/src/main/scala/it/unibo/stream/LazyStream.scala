package it.unibo.stream

import Streams.*
import it.unibo.algebraicDataType.Sequences.*

/** A real lazy [[Stream]] implementation. */
object LazyStream extends StreamADT:
  opaque type Stream[A] = () => Step[A]

  private enum Step[A]:
    case Empty()
    case Cons(head: () => A, tail: () => Stream[A])

  def empty[A](): Stream[A] = () => Step.Empty()

  def cons[A](hd: => A, tl: => Stream[A]): Stream[A] = () =>
    lazy val head = hd
    lazy val tail = tl
    Step.Cons(() => head, () => tail)

  def interleave[A](s1: Stream[A], s2: Stream[A]): Stream[A] = () => s1.step match
    case Step.Cons(h1, t1) => cons(h1(), interleave(s2, t1())).step
    case _ => s2.step

  extension [A](str: Stream[A])
    private def step: Step[A] = str()

    def toSequence: Sequence[A] = str.step match
      case Step.Empty() => Sequence.Nil()
      case Step.Cons(h, t) => Sequence.Cons(h(), t().toSequence)

    def take(n: Int): Stream[A] = () => str.step match
      case Step.Cons(h, t) if n > 0 => cons(h(), t().take(n - 1)).step
      case _ => empty().step

    def map[B](f: A => B): Stream[B] = () => str.step match
      case Step.Cons(h, t) => cons(f(h()), t().map(f)).step
      case _ => empty().step

    def filter(pred: A => Boolean): Stream[A] = () => str.step match
      case Step.Empty() => empty().step
      case Step.Cons(h, t) =>
        val hv = h()
        if pred(hv) then cons(hv, t().filter(pred)).step
        else t().filter(pred).step

    def takeWhile(pred: A => Boolean): Stream[A] = () => str.step match
      case Step.Cons(h, t) if pred(h()) => cons(h(), t().takeWhile(pred)).step
      case _ => empty().step

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


