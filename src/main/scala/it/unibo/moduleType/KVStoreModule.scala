package it.unibo.moduleType

object KVStoreModule:
  // KVStore trait
  export it.unibo.moduleType.KVStore
  // MapStore Implementation
  export it.unibo.moduleType.MapStore
  // ListStore Implementation
  export it.unibo.moduleType.ListStore

  // Default implementation
  given KVStore = MapStore
