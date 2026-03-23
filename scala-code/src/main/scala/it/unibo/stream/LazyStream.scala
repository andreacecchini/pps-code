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

  extension [A](str: Stream[A])
    private def step: Step[A] = str()

  def unfold[A, B](str: Stream[A])(
    onEmpty: => Stream[B],
    onCons: (=> A, => Stream[A]) => Stream[B]
  ): Stream[B] = () => str.step match
    case Step.Empty() => onEmpty.step
    case Step.Cons(h, t) => onCons(h(), t()).step

  def fold[A, B](str: Stream[A])(
    onEmpty: => B,
    onCons: (=> A, => Stream[A]) => B
  ): B = str.step match
    case Step.Empty() => onEmpty
    case Step.Cons(h, t) => onCons(h(), t())

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


