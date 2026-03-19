package it.unibo.stream

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
    def iterate[A](initial: => A)(next: A => A): Stream[A] =
      cons(initial, iterate(next(initial))(next))

    def fill[A](n: Int)(k: => A): Stream[A] =
      cycle(List(k)).take(n)

    def interleave[A](s1: Stream[A], s2: Stream[A]): Stream[A] = s1 match
      case Cons(h1, t1) => cons(h1(), interleave(s2, t1()))
      case _ => s2

    def cycle[A](l: List[A]): Stream[A] = l match
      case Nil => empty()
      case _ =>
        val start = (l.head, l.tail)
        iterate(start)((_, t) => if t == List.empty then start else (t.head, t.tail)).map(_._1)


    extension [A](s: Stream[A])
      /** stream to list. */
      def toList: List[A] = s match
        case Empty() => List.empty
        case Cons(h, t) => h() :: t().toList

      /** take the first [[n]] elements of the stream. */
      def take(n: Int): Stream[A] = s match
        case Cons(h, t) if n > 0 => cons(h(), t().take(n - 1))
        case _ => empty()

      /** take elements until [[pred]] is false. */
      def takeWhile(pred: A => Boolean): Stream[A] = s match
        case Cons(h, t) if pred(h()) => cons(h(), t().takeWhile(pred))
        case _ => empty()

      /** map each element in the stream by [[mapper]]. */
      def map[B](mapper: A => B): Stream[B] = s match
        case Cons(h, t) => cons(mapper(h()), t().map(mapper))
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
  val toFive = natural.takeWhile(_ < 5)
  println(toFive.toList)
  // ------
  // Fibonacci
  val fibonacci = iterate((0, 1))((a, b) => (b, a + b)).map(_._1)
  println(fibonacci.take(2).toList)
  println(fibonacci.take(5).toList)
  // ------
  // Interleaving
  val negative = iterate(0)(_ - 1)
  val interleaved = interleave(natural, negative)
  println(interleaved.take(10).toList)
  // ------
  // Cycling
  val l = List('a', 'b', 'c')
  val lCycled = cycle(l)
  println(lCycled.take(5).toList)
