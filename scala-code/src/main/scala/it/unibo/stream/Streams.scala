package it.unibo.stream

import it.unibo.algebraicDataType.Sequences.*
import Sequence.*

object Streams:
  trait StreamADT:
    /** Represents a stream of elements of type [[A]]. */
    type Stream[A]

    /** Returns an empty [[Stream]]. */
    def empty[A](): Stream[A]

    /** Create a new node holding [[hd]]. */
    def cons[A](hd: => A, tl: => Stream[A]): Stream[A]

    /**
     * Higher-order combinator for stream transformations.
     * Abstracts over the evaluation strategy (lazy vs strict).
     *
     * @param str     the stream to transform
     * @param onEmpty result when stream is empty
     * @param onCons  function applied to head and tail when stream is non-empty
     */
    def unfold[A, B](str: Stream[A])(
      onEmpty: => Stream[B],
      onCons: (=> A, => Stream[A]) => Stream[B]
    ): Stream[B]

    /**
     * Higher-order combinator for stream consumption.
     * Folds over the stream to produce a value.
     *
     * @param str     the stream to fold
     * @param onEmpty result when stream is empty
     * @param onCons  function applied to head and tail when stream is non-empty
     */
    def fold[A, B](str: Stream[A])(
      onEmpty: => B,
      onCons: (=> A, => Stream[A]) => B
    ): B

    /** Build an infinite [[Stream]] from [[initial]], advancing through [[next]]. */
    def iterate[A](initial: => A)(next: A => A): Stream[A] =
      cons(initial, iterate(next(initial))(next))

    /** Build a [[Stream]] of [[n]] elements holding [[k]]. */
    def fill[A](n: Int)(k: => A): Stream[A] =
      lazy val corec: Stream[A] = cons(k, corec)
      corec.take(n)

    /** Build a [[Stream]] interleaving elements from [[s1]] and [[s2]]. */
    def interleave[A](s1: Stream[A], s2: Stream[A]): Stream[A] =
      unfold(s1)(
        onEmpty = s2,
        onCons = (h, t) => cons(h, interleave(s2, t))
      )

    /** Build a [[Stream]] cycling through [[l]]. */
    def cycle[A](l: Sequence[A]): Stream[A] = l match
      case Sequence.Nil() => empty()
      case _ =>
        def loop(remainder: Sequence[A]): Stream[A] = remainder match
          case Sequence.Nil() => loop(l)
          case Sequence.Cons(h, t) => cons(h, loop(t))

        loop(l)

    extension [A](str: Stream[A])
      /** Convert [[str]] to a [[Sequence]]. */
      def toSequence: Sequence[A] =
        fold(str)(
          onEmpty = Sequence.Nil(),
          onCons = (h, t) => Sequence.Cons(h, t.toSequence)
        )

      /** Limits to firsts [[n]] elements of the stream. */
      def take(n: Int): Stream[A] =
        if n <= 0 then empty()
        else unfold(str)(
          onEmpty = empty(),
          onCons = (h, t) => cons(h, t.take(n - 1))
        )

      /** Maps each element of [[s]] using [[f]]. */
      def map[B](f: A => B): Stream[B] =
        unfold(str)(
          onEmpty = empty(),
          onCons = (h, t) => cons(f(h), t.map(f))
        )

      /** Filtering elements of [[s]] based on [[pred]]. */
      def filter(pred: A => Boolean): Stream[A] =
        unfold(str)(
          onEmpty = empty(),
          onCons = (h, t) =>
            if pred(h) then cons(h, t.filter(pred))
            else t.filter(pred)
        )

      /** Take elements while [[pred]] is satisfied. */
      def takeWhile(pred: A => Boolean): Stream[A] =
        unfold(str)(
          onEmpty = empty(),
          onCons = (h, t) =>
            if pred(h) then cons(h, t.takeWhile(pred))
            else empty()
        )
