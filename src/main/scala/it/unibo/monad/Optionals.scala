package it.unibo.monad

import Monads.*

object Optionals:
  // data structure for optionals
  enum Optional[A]:
    case Empty()
    case Just(a: A)

  // Algorithms
  object Optional:
    extension [A](m: Optional[A])
      def filter(pred: A => Boolean): Optional[A] =
        m.flatMap(a => if pred(a) then Just(a) else Empty())

  // Optional is a Monad!
  given Monad[Optional] with
    import Optional.{Just, Empty}

    def unit[A](a: A): Optional[A] = Just(a)

    extension [A](m: Optional[A])
      def flatMap[B](f: A => Optional[B]): Optional[B] = m match
        case Just(a) => f(a)
        case Empty() => Empty()


@main def testMonadicOptionals(): Unit =
  import Optionals.{*, given}
  import Optional.*
  import Monads.*
  import Monad.*

  println:
    for
      x <- Just(1)
      y <- Just(2)
      z <- Just(x * 2 + y)
    yield x + y + z
  println:
    Just(1).flatMap(
      x => Just(2).flatMap(
        y => Just(x * 2 + y).map(
          z => x + y + z
        )))
  val m1 = Just(1)
  val m2 = Just(2)
  println:
    map2(m1, m2)(_ + _)
