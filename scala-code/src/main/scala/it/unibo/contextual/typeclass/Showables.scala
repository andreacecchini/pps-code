package it.unibo.contextual.typeclass

/** Module containing Showable Type Class and operations over it. */
object Showables:
  /** Showable Type Class. */
  trait Showable[T]:
    def show(t: T): String

  /** Algorithms over Showable. */
  object Showable:
    extension [T: Showable](a: T)
      def show: String = summon[Showable[T]].show(a)

    def showCouple[T: Showable](t1: T, t2: T): String = s"(${t1.show}, ${t2.show})"


@main def testShowable(): Unit =
  import Showables.*
  import Showable.*
  given Showable[Int] with
    def show(t: Int): String = s"Int -> $t"

  println:
    0.show
  println:
    showCouple(1, 2)

