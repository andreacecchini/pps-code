package it.unibo.stream

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class StreamTest:

  import Streams.*
  import Stream.*

  val natural: Stream[Int] = iterate(0)(_ + 1)

  @Test def testTakeWhile(): Unit =
    assertEquals(List(0, 1, 2, 3, 4), natural.takeWhile(_ < 5).toList)

  @Test def testFill(): Unit =
    assertEquals(List(1, 1, 1, 1), fill(4)(1).toList)

  @Test def testFibonacci(): Unit =
    // f_{n} = f_{n-1} + f{n-2}
    // {(0, 1), (1, 1), (1, 2), (2, 3), (3, 5)...}
    // {0, 1, 1, 2, 3...}
    val fibonacci: Stream[Int] = iterate((0, 1))((a, b) => (b, a + b)).map(_._1)
    assertEquals(List(0, 1, 1, 2, 3), fibonacci.take(5).toList)

  @Test def testInterleave(): Unit =
    val negative = iterate(0)(_ - 1)
    assertEquals(List(0, 0, 1, -1, 2, -2, 3, -3, 4, -4), interleave(natural, negative).take(10).toList)

