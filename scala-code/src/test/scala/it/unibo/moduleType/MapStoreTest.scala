package it.unibo.moduleType

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*


class MapStoreTest:

  import KVStoreModule.*

  val storeModule: KVStore = MapStore
  import storeModule.*

  @Test def testEmptyStore(): Unit =
    val store: Store[String, Int] = emptyStore()
    assertTrue:
      store.isEmpty
