package it.unibo.highOrder

import scala.annotation.tailrec

/** High level of abstraction thanks High Order Functions. */
def mapReduce(f: Int => Int, combine: (Int, Int) => Int, zero: Int)(
    a: Int,
    b: Int
): Int =
  @tailrec
  def loop(a: Int)(acc: Int): Int = (a, b) match
    case (a, b) if a > b => acc
    case _               => loop(a + 1)(combine(acc, f(a)))
  loop(a)(zero)

def product(f: Int => Int): (Int, Int) => Int = mapReduce(f, _ * _, 1)

def sum(f: Int => Int): (Int, Int) => Int = mapReduce(f, _ + _, 0)

/** Fixed point => all x such that => x = f(x) */
def fixedPoint(f: Double => Double)(guess: Double): Double =
  def isCloseEnough(x: Double, y: Double): Boolean =
    def abs(a: Double): Double = if a >= 0 then a else -a
    val epsilon = 1e-12
    val diff = abs(x - y)
    if abs(x) < epsilon then diff < epsilon
    else (diff / abs(x)) < epsilon
  // Newton method
  // x f(x) f(f(x)) f(f(f(x))) f(f(f(f(x)))) ... until f^i(x) = f^{i+1}(x) (or close enough).
  @tailrec
  def iterate(guess: Double): Double =
    val next = f(guess)
    if isCloseEnough(guess, next) then guess else iterate(next)
  iterate(1.0)

def averageDump(f: Double => Double): Double => Double = x => (x + f(x)) / 2

// Note the level of abstraction due the use of High Order Functions
// Appreciate even the substitution model
// sqrt(x) = such y such as y = x / y => sqrt(x) is a fixed point for the function f(y) = x / y
def sqrt(x: Double): Double = fixedPoint(averageDump(y => x / y))(1.0)

@main def main(): Unit =
  // Functions as parameters
  println(product(x => x)(1, 2))
  def factorial(n: Int): Int = product(identity)(1, n)
  println(factorial(0))
  println(factorial(1))
  println(factorial(6))
  // Functions as return value
  println(sqrt(2)) // 1.4142...
  println(sqrt(4)) // 2.0
  println(sqrt(1)) // 1.0
  println(sqrt(1.0e50)) // 1.0e25
  println(sqrt(0)) // 0.0 (9.094947017729282E-13)
