package it.unibo.moduleType

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import KVStoreModule.*

abstract class KVStoreTest(val storeModule: KVStore):

  import storeModule.*

  val empty: Store[String, Int] = emptyStore()
  val k1 = "i1"
  val v1 = 1
  val v2 = 2

  @Test def testEmptyStore(): Unit =
    assertTrue:
      empty.isEmpty

  @Test def testAdd(): Unit =
    val s1 = empty.put(k1, v1)
    assertFalse:
      s1.isEmpty
    assertEquals(Some(v1), s1.get(k1))

  @Test def testRemove(): Unit =
    val s1 = empty.put(k1, v1)
    val s2 = s1.remove(k1)
    assertTrue:
      s2.isEmpty
    assertEquals(None, s2.get(k1))

  @Test def testOverride(): Unit =
    val s1 = empty.put(k1, v1)
    val s2 = s1.put(k1, v2)
    assertEquals(Some(v2), s2.get(k1))
