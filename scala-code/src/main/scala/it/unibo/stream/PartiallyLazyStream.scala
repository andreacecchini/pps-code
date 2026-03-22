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

  /** empty node */
  def empty[A](): Stream[A] = Empty()

  /** lazy node stream. */
  def cons[A](hd: => A, tl: => Stream[A]): Stream[A] =
    lazy val head = hd
    lazy val tail = tl
    Cons(() => head, () => tail)

  /** build an infinite stream. */
  def iterate[A](initial: => A)(next: A => A): Stream[A] =
    cons(initial, iterate(next(initial))(next))

  def fill[A](n: Int)(k: => A): Stream[A] =
    cycle(Sequence.Cons(k, Sequence.Nil())).take(n)

  def interleave[A](s1: Stream[A], s2: Stream[A]): Stream[A] = s1 match
    case Cons(h1, t1) => cons(h1(), interleave(s2, t1()))
    case _ => s2

  /** build an infinite stream cycling [[l]]. */
  def cycle[A](l: Sequence[A]): Stream[A] = l match
    case Sequence.Nil() => empty()
    case _ =>
      def loop(remainder: Sequence[A]): Stream[A] = remainder match
        case Sequence.Nil() => loop(l)
        case Sequence.Cons(h, t) => cons(h, loop(t))

      loop(l)


  extension [A](str: Stream[A])
    /** stream to list. */
    def toSequence: Sequence[A] = str match
      case Empty() => Sequence.Nil()
      case Cons(h, t) => Sequence.Cons(h(), t().toSequence)

    /** take the first [[n]] elements of the stream. */
    def take(n: Int): Stream[A] = str match
      case Cons(h, t) if n > 0 => cons(h(), t().take(n - 1))
      case _ => empty()

    /** take elements until [[pred]] is false. */
    def takeWhile(pred: A => Boolean): Stream[A] = str match
      case Cons(h, t) if pred(h()) => cons(h(), t().takeWhile(pred))
      case _ => empty()

    /** map each element in the stream by [[mapper]]. */
    def map[B](mapper: A => B): Stream[B] = str match
      case Cons(h, t) => cons(mapper(h()), t().map(mapper))
      case _ => empty()

    /** filter elements by [[pred]]. */
    def filter(pred: A => Boolean): Stream[A] = str match
      case Cons(h, t) if pred(h()) => cons(h(), t().filter(pred))
      case Cons(h, t) => t().filter(pred)
      case _ => Empty()

@main def testStream(): Unit =
  import PartiallyLazyStream.*

  // Build
  val s0: Stream[Int] = empty() // {}
  val s1: Stream[Int] = cons(1, s0) // {1}
  val natural = iterate(0)(_ + 1) // {1, 2, ...}
  // ------
  // To list
  println(s1.toSequence)
  // println(natural.toSequence) // StackOverflow!
  // ------
  // Take
  val toTen = natural.take(10) // {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
  println(toTen.toSequence)
  val toFive = natural.takeWhile(_ < 5)
  println(toFive.toSequence)
  // ------
  // Fibonacci
  val fibonacci = iterate((0, 1))((a, b) => (b, a + b)).map(_._1)
  println(fibonacci.take(2).toSequence)
  println(fibonacci.take(5).toSequence)
  // ------
  // Interleaving
  val negative = iterate(0)(_ - 1)
  val interleaved = interleave(natural, negative)
  println(interleaved.take(10).toSequence)
  // ------
  // Cycling
  import Sequence.*
  val l = Cons('a', Cons('b', Cons('c', Nil())))
  println(cycle(l).take(5).toSequence)
