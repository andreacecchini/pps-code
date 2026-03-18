package it.unibo.stream

import it.unibo.stream.Streams.Stream.*

/** Streams module */
object Streams:
  /** Stream type */
  enum Stream[A]:
    private case Empty()
    private case Cons(head: () => A, tail: () => Stream[A])

  /** Stream algorithms */
  object Stream:
    /** empty string */
    def empty[A](): Stream[A] = Empty()

    /** lazy node stream. */
    def cons[A](hd: => A, tl: => Stream[A]): Stream[A] =
      lazy val head = hd
      lazy val tail = tl
      Cons(() => head, () => tail)

    /** build an infinite stream. */
    def iterate[A](initial: A)(next: A => A): Stream[A] =
      cons(initial, iterate(next(initial))(next))

    extension [A](s: Stream[A])
      /** stream to list. */
      def toList: List[A] = s match
        case Empty() => List.empty
        case Cons(h, t) => h() :: t().toList

      /** take the first [[n]] elements of the stream. */
      def take(n: Int): Stream[A] = s match
        case Cons(h, t) if n > 0 => cons(h(), t().take(n - 1))
        case _ => empty()


@main def testStream(): Unit =
  import Streams.*
  import Stream.*

  // Build
  val s0: Stream[Int] = empty() // {}
  val s1: Stream[Int] = cons(1, s0) // {1}
  val natural = iterate(0)(_ + 1) // {1, 2, ...}
  // ------
  // To list
  println(s1.toList)
  // println(natural.toList) // StackOverflow!
  // ------
  // Take
  val toTen = natural.take(10) // {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
  println(toTen.toList)
