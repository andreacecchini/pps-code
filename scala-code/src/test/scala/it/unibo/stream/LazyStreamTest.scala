package it.unibo.stream

import Streams.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

val streamImpl: StreamADT = LazyStream
given StreamADT = streamImpl

class LazyStreamTest extends StreamTest:
  import streamImpl.*

  @Test def testLazyFilter(): Unit =
    var c1 = 0
    var c2 = 0
    val str = cons({c1 = c1 + 1; 1}, cons({c2 = c2 + 1; 2}, empty()))
    str.filter(_ => false)
    assertEquals(0, c1)
    assertEquals(0, c2)
