package it.unibo.evaluationOrder

val f1: (Boolean, Int) => Int = (cond, x) => if cond then x + 1 else 0

// What would be the type of `f2`?
// All declarations are fine!
// val f2: (Boolean, Int) => Int = f3
// val f2: (Boolean, => Int) => Int = f3
// What if type is omitted?
val f2 = f3

def f3(cond: Boolean, x: => Int): Int = if cond then x + 1 else 0

@main def testEvaluationOrder(): Unit =
  var c1: Int = 0
  var c2: Int = 0
  def x1 = {
    println(f"f1 computed `0` ${c1 = c1 + 1; c1} times ")
    0
  }
  def x2 = {
    println(f"f2 computed `0` ${c2 = c2 + 1; c2} times")
    0
  }
  println("--------Case 1--------")
  f1(true, x1) // f1 computed `0` 1 times
  f2(true, x2) // f2 computed `0` 1 times
  println("--------Case 2--------")
  c1 = 0
  c2 = 0
  f1(false, x1) // f1 computed `0` 1 times
  /*
     While IntelliJ reports `f2` as `(Boolean, Int) => Int`, it actually exhibits call-by-name semantics,
     behaving as if its type were (Boolean, => Int) => Int.
     In my view, there is an inconsistency.
     If I had made the type explicit as `(Boolean, Int) => Int` in the declaration, `x2` would be evaluated before
     the function substitution as expected.
  */
  f2(false, x2)
