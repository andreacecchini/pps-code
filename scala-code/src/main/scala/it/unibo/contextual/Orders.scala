package it.unibo.contextual

import it.unibo.algebraicDataType.Sequences.*
import Sequence.*

import scala.annotation.tailrec

object Orders:
  /** IntOrderingModuleType trait. */
  trait Order[A]:
    def greater(x: A, y: A): Boolean

  // Anonymous type class implementation
  given Order[Int] with
    def greater(x: Int, y: Int): Boolean = x >= y

  /** An [[String]] [[Order]] implementation. */
  private object LexicGraphicalOrder extends Order[String]:
    def greater(x: String, y: String): Boolean = x >= y

  given Order[String] = LexicGraphicalOrder

@main def testContextualModules(): Unit =
  import Orders.*
  @tailrec
  def max[A](s: Sequence[A])(using ordering: Order[A]): A = s match
    case Cons(h1, Cons(h2, t)) => max(Cons(if ordering.greater(h1, h2) then h1 else h2, t))
    case Cons(h, Nil()) => h
  // import ContextualModules.given
  // Why does it work without given import? Only with contextual modules.
  println(max(Cons(10, Cons(30, Cons(20, Nil())))))
  // Resolved by the "given table" in the compiler.
  // This mechanism breaks dependencies similar to factory method.
  val ord = summon[Order[Int]]
  println:
    max(Cons(10, Cons(30, Cons(20, Nil()))))(using ord)
  // Now I can call max on type A such as an OrderingModuleType[A] exits!
  // Achieving ad-hoc polymorphism!
  println:
    max(Cons("b", Cons("a", Cons("c", Nil()))))
