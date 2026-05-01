package it.unibo.contextual

import scala.annotation.tailrec

object MathModules:
  trait MathModuleType:
    /** Computes [[n]]!. */
    def factorial(n: Int): Int

    /** Computes [[a]]&#94;[[b]]. */
    def exp(a: Double)(b: Int): Double

  object NaiveMathModule extends MathModuleType:
    def factorial(n: Int): Int = n match
      case 0 | 1 => 1
      case n => n * factorial(n - 1)

    def exp(a: Double)(b: Int): Double = b match
      case 0 => 1
      case _ => a * exp(a)(b - 1)

  object ProductionMathModule extends MathModuleType:
    def factorial(n: Int): Int =
      @tailrec
      def loop(n: Int, acc: Int): Int = n match
        case 0 | 1 => acc
        case _ => loop(n - 1, n * acc)

      loop(n, 1)

    def exp(a: Double)(b: Int): Double = b match
      case 0 => 1
      case b if b % 2 == 0 => {
        val half = exp(a)(b / 2);
        half * half
      }
      case _ => a * exp(a)(b - 1)

  given MathModuleType = ProductionMathModule

@main def testMathModules(): Unit =
  import MathModules.*

  // probability of having x successes over n trials, where each success has prob. p
  // abstracting from specific implementation of the math module
  def binomialProbability(n: Int, x: Int, p: Double)(using mm: MathModuleType): Double =
    import mm.*
    factorial(n) / factorial(x) / factorial(n - x) * exp(p)(x) * exp(1 - p)(n - x)

  // probability of 6 heads on 10 coin tosses
  println:
    binomialProbability(10, 6, 0.5)(using NaiveMathModule)
  println:
    binomialProbability(10, 6, 0.5)(using ProductionMathModule)
  import MathModules.given
  println:
    binomialProbability(10, 6, 0.5) // Using Production module
