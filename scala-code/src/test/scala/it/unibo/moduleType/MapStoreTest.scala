package it.unibo.moduleType

import org.junit.jupiter.api.{BeforeEach, Test}
import org.junit.jupiter.api.Assertions.*


class MapStoreTest:

  import KVStoreModule.*

  val storeModule: KVStore = MapStore
  import storeModule.*

  val empty: Store[String, Int] = emptyStore()
  val k1 = "i1"

  @Test def testEmptyStore(): Unit =
    assertTrue:
      empty.isEmpty


  @Test def testAdd(): Unit =
    val s1 = empty.put(k1, 1)
    assertFalse:
      s1.isEmpty
    assertEquals(Some(1), s1.get(k1))

  @Test def testRemove(): Unit =
    val s1 = empty.put(k1, 1)
    val s2 = s1.remove(k1)
    assertTrue:
      s2.isEmpty
    assertEquals(None, s2.get(k1))

  @Test def testOverride(): Unit =
    val s1 = empty.put(k1, 1)
    val s2 = s1.put(k1, 2)
    assertEquals(Some(2), s2.get(k1))
