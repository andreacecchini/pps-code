package it.unibo.monad

import it.unibo.monad.Monads.Monad
import it.unibo.monad.Monads.Monad.map2
import scala.annotation.init
import it.unibo.monad.Monads.Monad.seq

object States:
  /** Data structure holding state evolution.
    */
  case class State[S, A](run: S => (S, A))

  object State:
    extension [S, A](m: State[S, A])
      def apply(s: S): (S, A) = m match
        case State(run) => run(s)

  // type lambdas
  given stateMonad[S]: Monad[[A] =>> State[S, A]] with
    // Run the state without evolution but with the produced value
    def unit[A](a: A): State[S, A] = State(s => (s, a))
    extension [A](m: State[S, A])
      // Run the state -> Get produced value -> use it to build new state
      def flatMap[B](f: A => State[S, B]): State[S, B] =
        State(s1 =>
          m(s1) match
            case (s2, a) => val m2 = f(a); m2(s2)
        )
end States

@main def testStates(): Unit =
  import States.{*, given}
  val s: State[Int, Int] = State((counter) => (counter + 1, counter))
  println(s run 0)
  val s3: State[Int, Int] = s.flatMap(s1 => s).flatMap(s2 => s)
  println(s3 run 0)

import States.*
trait CounterState:
  type Counter
  def initialCounter(): Counter
  def inc(): State[Counter, Unit]
  def dec(): State[Counter, Unit]
  def reset(): State[Counter, Unit]
  def get(): State[Counter, Int]
  def nop(): State[Counter, Unit]

object CounterStateImpl extends CounterState:
  opaque type Counter = Int
  def initialCounter(): Counter = 0
  def inc(): State[Counter, Unit] = State(i => (i + 1, ()))
  def dec(): State[Counter, Unit] = State(i => (i - 1, ()))
  def reset(): State[Counter, Unit] = State(_ => (0, ()))
  def get(): State[Counter, Int] = State(i => (i, i))
  def nop(): State[Counter, Unit] = State(i => (i, ()))

@main def testStateMonad(): Unit =
  import States.{*, given}, State.*
  val counterState: CounterState = CounterStateImpl
  import counterState.*
  def increment(n: Int): State[Counter, Unit] = n match
    case 0 => nop()
    case _ => for _ <- increment(n - 1); _ <- inc() yield {}

  println:
    inc().run(initialCounter())
  println:
    seq(inc(), inc()).run(initialCounter())

  val session: State[Counter, Int] =
    inc().flatMap { _ =>
      reset().flatMap { _ =>
        increment(5).flatMap { _ =>
          get().flatMap { v =>
            reset().map { _ => v }
          }
        }
      }
    }
    // for
    //   _ <- inc()
    //   _ <- reset()
    //   _ <- increment(5)
    //   v <- get()
    //   _ <- reset()
    // yield v
  println:
    session.run(initialCounter())
