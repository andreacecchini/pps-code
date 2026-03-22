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

    /** Build an infinite [[Stream]] from [[initial]], advancing through [[next]]. */
    def iterate[A](initial: => A)(next: A => A): Stream[A]

    /** Build a [[Stream]] of [[n]] elements holding [[k]]. */
    def fill[A](n: Int)(k: => A): Stream[A]

    /** Build a [[Stream]] interleaving elements from [[s1]] and [[s2]]. */
    def interleave[A](s1: Stream[A], s2: Stream[A]): Stream[A]

    /** Build a [[Stream]] cycling through [[l]]. */
    def cycle[A](l: Sequence[A]): Stream[A]

    extension [A](str: Stream[A])
      /** Convert [[str]] to a [[Sequence]]. */
      def toSequence: Sequence[A]
      /** Limits to firsts [[n]] elements of the stream. */
      def take(n: Int): Stream[A]
      /** Maps each element of [[s]] using [[f]]. */
      def map[B](f: A => B): Stream[B]
      /** Filtering elements of [[s]] based on [[pred]]. */
      def filter(pred: A => Boolean): Stream[A]
      /** Take elements while [[pred]] is satisfied. */
      def takeWhile(pred: A => Boolean): Stream[A]
