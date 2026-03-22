package it.unibo.moduleType

private[moduleType] object MapStore extends KVStore:
  opaque type Store[K, V] = Map[K, V]

  def emptyStore[K, V](): Store[K, V] = Map.empty

  extension [K, V](s: Store[K, V])
    def isEmpty: Boolean = s.isEmpty
