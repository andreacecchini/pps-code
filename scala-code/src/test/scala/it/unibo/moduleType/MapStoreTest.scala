package it.unibo.moduleType

import org.junit.jupiter.api.{BeforeEach, Test}
import org.junit.jupiter.api.Assertions.*


class MapStoreTest:

  import KVStoreModule.*
  import Option.*
  val storeModule: KVStore = MapStore
  import storeModule.*

  val empty: Store[String, Int] = emptyStore()

  @Test def testEmptyStore(): Unit =
    assertTrue:
      empty.isEmpty

  @Test def testAdd(): Unit =
    val s1 = empty.put("i1", 1)
    assertFalse:
      s1.isEmpty
    assertEquals(Some(1), s1.get("i1"))

