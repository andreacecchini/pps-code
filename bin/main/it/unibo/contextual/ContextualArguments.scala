package it.unibo.contextual

import it.unibo.algebraicDataType.Sequences.*
import Sequence.*

import scala.annotation.tailrec

object ContextualArguments:
  @tailrec
  def max(s: Sequence[Int])(using orElse: Int): Int = s match
    case Cons(h1, Cons(h2, t)) => max(Cons(if h1 >= h2 then h1 else h2, t))
    case Cons(h, Nil()) => h
    case _ => orElse

  given standardOrElse: Int = -1


@main def testContextualArguments(): Unit =
  import ContextualArguments.*

  println:
    max(Cons(10, Cons(30, Cons(20, Nil()))))(using -1)
  import ContextualArguments.given
  println:
    max(Nil())

