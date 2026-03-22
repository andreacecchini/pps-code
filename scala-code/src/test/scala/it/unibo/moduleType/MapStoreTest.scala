package it.unibo.moduleType

import org.junit.jupiter.api.{BeforeEach, Test}
import org.junit.jupiter.api.Assertions.*


class MapStoreTest:

  import KVStoreModule.*

  val storeModule: KVStore = MapStore
  import storeModule.*

  val empty: Store[String, Int] = emptyStore()

  @Test def testEmptyStore(): Unit =
    assertTrue:
      empty.isEmpty
