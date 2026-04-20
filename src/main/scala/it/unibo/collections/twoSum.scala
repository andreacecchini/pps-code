package it.unibo.collections

import scala.util.boundary, boundary.break

/** Return an [[Option]] wrapping a [[Tuple]] containing the indexes of two
  * elements in [[in]] such as `in(i) + in(j) = target`. If solution does not
  * exist, then [[None]] is returned.
  */
def twoSum(in: Array[Int], target: Int): Option[(Int, Int)] =
  boundary:
    // Note that using mutable.Map won't break any part of code.
    var map = Map.empty[Int, Int]
    for (current, i) <- in.zipWithIndex do
      val complement = target - current
      if map.contains(complement) then break(Some((map(complement), i)))
      else map += (current -> i)
    None

@main def testTwoSum(): Unit =
  val in = Array(0, 4, 5, 2)
  val target = 2
  twoSum(in, target) match
    case Some((i, j)) => println(s"Solution: ($i, $j)")
    case None         => println("No solution has been found...")
