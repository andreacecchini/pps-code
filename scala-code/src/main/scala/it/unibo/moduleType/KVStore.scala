package it.unibo.moduleType

private[moduleType] trait KVStore:
  /** Abstracts a data structure which holds (key, value) pairs. */
  type Store[K, V]

  /** Create an empty [[Store]] */
  def emptyStore[K, V](): Store[K, V]

  extension [K, V](s: Store[K, V])
    /** Returns whether [[s]] is empty. */
    def isEmpty: Boolean

    /** Put a new element in [[s]]. */
    def put(k: K, v: V): Store[K, V]

    /** Get an [[Option]] containing element associated with [[k]] if present. */
    def get(k: K): Option[V]

    /** Remove the element associate with [[k]]. */
    def remove(k: K): Store[K, V]
