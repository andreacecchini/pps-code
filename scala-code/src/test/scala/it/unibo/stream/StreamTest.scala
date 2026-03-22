package it.unibo.stream

import Streams.*
import it.unibo.algebraicDataType.Sequences.*
import Sequence.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

abstract class StreamTest(using streamImpl: StreamADT):

  import streamImpl.*

  private val naturals: Stream[Int] = iterate(0)(_ + 1)
  private val emptyStream: Stream[Int] = empty()
  private val str1 = cons(1, emptyStream)

  @Test def testEmptyStream(): Unit =
    assertEquals(Nil(), emptyStream.toSequence)

  @Test def testStreamWithOneElement(): Unit =
    assertEquals(Cons(1, Nil()), str1.toSequence)

  @Test def testIterate(): Unit =
    assertEquals(Cons(0, Cons(1, Cons(2, Nil()))), naturals.take(3).toSequence)

  @Test def testMap(): Unit =
    val inc: Int => Int = _ + 1
    assertEquals(Nil(), emptyStream.map(inc).toSequence)
    assertEquals(Cons(2, Nil()), str1.map(inc).toSequence)

  @Test def testFilter(): Unit =
    assertEquals(Nil(), emptyStream.filter(_ => true).toSequence)
    assertEquals(Cons(1, Nil()), str1.filter(_ => true).toSequence)
    assertEquals(Nil(), str1.filter(_ => false).toSequence)

  @Test def testTakeWhile(): Unit =
    assertEquals(Cons(0, Cons(1, Cons(2, Nil()))), naturals.takeWhile(_ < 3).toSequence)

  @Test def testFill(): Unit =
    assertEquals(Cons(1, Cons(1, Cons(1, Cons(1, Nil())))), fill(4)(1).toSequence)

  @Test def testInterleave(): Unit =
    val negatives = iterate(0)(_ - 1)
    assertEquals(Cons(0, Cons(0, Cons(1, Cons(-1, Nil())))), interleave(naturals, negatives).take(4).toSequence)

  @Test def testCycle(): Unit =
    assertEquals(empty(), cycle(Nil()))
    assertEquals(Cons('a', Cons('a', Nil())), cycle(Cons('a', Nil())).take(2).toSequence)
    assertEquals(Cons('a', Cons('b', Cons('c', Cons('a', Cons('b', Nil()))))), cycle(Cons('a', Cons('b', Cons('c', Nil())))).take(5).toSequence)
