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

  // For yield monadic construct
  val m: Optional[Int] = for
    x <- Just(1)
    y <- Just(2)
    z <- Just(x * 2 + y)
  yield x + y + z
  // This becomes
  val m2: Optional[Int] = Just(1).flatMap(
    x => Just(2).flatMap(
      y => Just(x * 2 + y).map(
        z => x + y + z
      )))
  println:
    m
  println:
    m2
  val m3 = m.filter(_ > 10)
  println:
    m3
