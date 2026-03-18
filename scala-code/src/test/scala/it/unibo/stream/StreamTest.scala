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

