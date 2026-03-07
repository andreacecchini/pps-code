package it.unibo.tailrec

import scala.annotation.tailrec

@tailrec
def gdc(a: Int, b: Int): Int = b match
  case 0 => a
  case _ => gdc(b, a % b)

/** fact(n) = n! = n*(n-1)! */
def badFactorial(n: Int): Int = n match
  case 0 | 1 => 1
  case _     => n * badFactorial(n - 1)

def factorial(n: Int): Int =
  /*
   * Bottom up approach
   * 1
   * n * 1
   * n * n-1 * 1
   * n * n-1 * n-2 * 1
   * n * n-1 * n-2 * ... * 1 = n!
   *  */
  @tailrec
  def _fact(n: Int, acc: Int): Int = n match
    case 0 | 1 => acc
    case _     => _fact(n - 1, n * acc)
  _fact(n, 1)

/** ƒ(a) + ƒ(a+1) + ... + ƒ(b) */
def sum(f: Int => Int)(a: Int, b: Int): Int =
  @tailrec
  def loop(a: Int, acc: Int): Int = (a, b) match
    case (a, b) if a > b => acc
    case _               => loop(a + 1, acc + f(a))
  loop(a, 0)

@main def main(): Unit =
  /*
   * gdc(14, 21) (*)
   * if 21 == 0 then 14 else gdc(21, 14 % 21)
   * gdc(21, 14) (*)
   * if 14 == 0 then 21 else gdc(14, 21 % 14)
   * gdc(14, 7) (*)
   * if 7 == 0 then 14 else gdc(7, 14 % 7)
   * gdc(7, 0) (*)
   * if 0 == 0 then 7 else gdc(0, 7 % 0)
   * 7
   * IS FLAT!
   * */
  /*
   * (*) Can reuse gdc(14, 21) stack frame!
   * => Achieving loop benefit => no stack overflow
   * */
  println(gdc(14, 21)) // 7
  println(factorial(6)) // 720
  // 1 + 2 + 3 + 4 + 5
  println(sum(x => x)(1, 5))
  // 1 + 4 + 9 + 16 + 25
  println(sum(x => x * x)(1, 5))
  // fact(1) + fact(2) + fact(3) + fact(4) + fact(5)
  println(sum(factorial)(1, 5))
