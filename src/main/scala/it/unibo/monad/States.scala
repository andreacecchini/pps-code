package it.unibo.monad

import it.unibo.monad.Monads.Monad
import it.unibo.monad.Monads.Monad.map2
import scala.annotation.init
import it.unibo.monad.Monads.Monad.seq

object States:
  /** Data structure holding state evolution.
    */
  opaque type State[S, A] = S => (S, A)
  object State:
    def apply[S, A](next: S => (S, A)): State[S, A] = next
    extension [S, A](s: State[S, A]) infix def run(current: S): (S, A) = s(current)

  // type lambdas
  given stateMonad[S]: Monad[[A] =>> State[S, A]] with
    // Run the state without evolution but with the produced value
    def unit[A](a: A): State[S, A] = s => (s, a)
    extension [A](m: State[S, A])
      // Run the state -> Get produced value -> use it to build new state
      def flatMap[B](f: A => State[S, B]): State[S, B] =
        s1 =>
          m(s1) match
            case (s2, a) => f(a)(s2)
end States

@main def testStates(): Unit =
  import States.{*, given}
  val s: State[Int, Int] = State((counter) => (counter + 1, counter))
  println(s run 0)
  val s3: State[Int, Int] = s.flatMap(s1 => s).flatMap(s2 => s)
  println(s3)

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
    get() run initialCounter()

  println:
    inc() run initialCounter()

  println:
    seq(inc(), get()) run initialCounter()

  println:
    (for
      _ <- inc()
      _ <- reset()
      _ <- increment(5)
      v <- get()
      _ <- reset()
      _ <- inc()
    yield v) run initialCounter()
