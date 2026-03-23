package it.unibo.contextual.typeclass

import it.unibo.algebraicDataType.Sequences.*
import it.unibo.algebraicDataType.Sequences.Sequence.*

import scala.annotation.tailrec

object Orders:
  /** Ordered Type class */
  trait Ordered[A]:
    def greater(x: A, y: A): Boolean

  /** Some algorithms on [[Ordered]]. */
  object Ordered:
    @tailrec
    def max[A: Ordered](s: Sequence[A]): Option[A] =
      s match
        case Cons(h1, Cons(h2, t)) => max(Cons(if summon[Ordered[A]].greater(h1, h2) then h1 else h2, t))
        case Cons(h, Nil()) => Some(h)
        case Nil() => None

  // Anonymous type class implementation
  given Ordered[Int] with
    def greater(x: Int, y: Int): Boolean = x >= y

  /** An [[String]] [[Ordered]] implementation. */
  private object LexicoGraphicalOrder extends Ordered[String]:
    def greater(x: String, y: String): Boolean = x >= y

  given Ordered[String] = LexicoGraphicalOrder

@main def testContextualModules(): Unit =
  import Orders.{*, given}
  import Ordered.*

  // import ContextualModules.given
  // Why does it work without given import? Only with contextual modules.
  println(max(Cons(10, Cons(30, Cons(20, Nil())))))
  // Resolved by the "given table" in the compiler.
  // This mechanism breaks dependencies similar to factory method.
  val ord = summon[Ordered[Int]]
  println:
    max(Cons(10, Cons(30, Cons(20, Nil()))))(using ord)
  // Now I can call max on type A such as an OrderingModuleType[A] exits!
  // Achieving ad-hoc polymorphism!
  println:
    max(Cons("b", Cons("a", Cons("c", Nil()))))
