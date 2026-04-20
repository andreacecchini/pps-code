package it.unibo.contextual

import it.unibo.algebraicDataType.Sequences.*
import Sequence.*

import scala.annotation.tailrec

object ContextualStrategies:
  /** partial function */
  @tailrec
  def max(s: Sequence[Int])(using greater: (Int, Int) => Boolean): Int = s match
    case Cons(h1, Cons(h2, t)) => max(Cons(if greater(h1, h2) then h1 else h2, t))
    case Cons(h, Nil()) => h

  given ((Int, Int) => Boolean) = _ >= _

@main def testContextualStrategies(): Unit =
  import ContextualStrategies.*

  println:
    // This actually remembers strategy pattern (comparator)
    max(Cons(10, Cons(30, Cons(20, Nil()))))(using _ >= _)
  println:
    max(Cons(10, Cons(30, Cons(20, Nil()))))(using _ <= _)
  import ContextualStrategies.given
  println:
    max(Cons(10, Cons(30, Cons(20, Nil()))))


