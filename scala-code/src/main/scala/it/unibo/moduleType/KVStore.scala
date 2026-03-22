package it.unibo.moduleType

private[moduleType] trait KVStore:
  /** Abstracts a data structure which holds (key, value) pairs. */
  type Store[K, V]

  /** Create an empty [[Store]] */
  def emptyStore[K, V](): Store[K, V]

  extension [K, V](s: Store[K, V])
    /** Returns whether [[s]] is empty. */
    def isEmpty: Boolean
