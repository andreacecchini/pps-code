package it.unibo.sqrt

import scala.annotation.tailrec

def sqrt(x: Double): Double =
  @tailrec
  def sqrtIter(guess: Double, x: Double): Double =
    def isGoodEnough(guess: Double, x: Double): Boolean =
      def abs(x: Double): Double = if x >= 0 then x else -x

      abs(guess * guess - x) < 0.001

    def improve(guess: Double, x: Double): Double =
      (guess + x / guess) / 2

    if isGoodEnough(guess, x) then guess else sqrtIter(improve(guess, x), x)

  sqrtIter(1.0, x)

def square(x: Double): Double = x * x

@main def main(): Unit =
  println(sqrt(4.0)) // Close to 2.0
  println(sqrt(2.0)) // Close to 1.41
  println(sqrt(0.1e-20)) // Does it terminate?
  println(square(sqrt(0.1e-20))) // Check it -> need to study floating point arithmetic!
