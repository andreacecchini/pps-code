package it.unibo.contextual

import it.unibo.algebraicDataType.Sequences.*
import Sequence.*

import scala.annotation.tailrec

object ContextualModules:
  /** IntOrderingModuleType trait. */
  trait IntOrderingModuleType:
    def greater(x: Int, y: Int): Boolean

  /** A classic [[IntOrderingModuleType]] implementation. */
  private object MyIntOrderingModule extends IntOrderingModuleType:
    def greater(x: Int, y: Int): Boolean = x >= y

  @tailrec
  def max(s: Sequence[Int])(using ordering: IntOrderingModuleType): Int = s match
    case Cons(h1, Cons(h2, t)) => max(Cons(if ordering.greater(h1, h2) then h1 else h2, t))
    case Cons(h, Nil()) => h

  given IntOrderingModuleType = MyIntOrderingModule

@main def testContextualModules(): Unit =
  import ContextualModules.*
  // import ContextualModules.given
  // Why does it work without given import? Only with contextual modules.
  println(max(Cons(10, Cons(30, Cons(20, Nil())))))
  // Resolved by the "given table" in the compiler.
  // This mechanism breaks dependencies similar to factory method.
  val ord = summon[IntOrderingModuleType]
  println:
    max(Cons(10, Cons(30, Cons(20, Nil()))))(using ord)
