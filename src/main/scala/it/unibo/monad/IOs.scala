package it.unibo.monad

import it.unibo.monad.Monads.Monad

object IOs:
  /** A Data structure holding a computation with input/output
    */
  case class IO[A](exec: () => A)

  object IO:
    def read(): IO[String] = IO(() => scala.io.StdIn.readLine)
    def write[A](a: A): IO[A] = IO(() => { println(a); a })
    def compute[A](a: => A): IO[A] = IO(() => a)
    def nop(): IO[Unit] = compute({})

  given Monad[IO] with
    def unit[A](a: A): IO[A] = IO.compute(a)
    extension [A](m: IO[A])
      def flatMap[B](f: A => IO[B]): IO[B] = m match
        case IO(e) => f(e())

end IOs

@main def testIOs(): Unit =
  import IOs.*
  val r = IO.read()
  val w = IO.write(r.exec())
  w.exec()
  val c = IO.compute(r.exec().toInt)
  c.exec()
  IO.nop()

@main def testIOsAsMonad(): Unit =
  import IOs.*
  import IO.*
  // Capturing side effects in pure functional settings
  val program = for
    _ <- write("What x you want to increment?")
    i <- read()
    x <- compute(i.toInt)
    y <- compute(x + 1)
    _ <- write(s"x: $x => inc(x) = $y")
  yield nop()
  program.exec()
