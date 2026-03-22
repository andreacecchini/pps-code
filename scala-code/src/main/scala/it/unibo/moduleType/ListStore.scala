package it.unibo.moduleType


private[moduleType] object ListStore extends KVStore:
  opaque type Store[K, V] = List[(K, V)]

  def emptyStore[K, V](): Store[K, V] = List.empty

  extension [K, V](s: Store[K, V])
    def isEmpty: Boolean = s.isEmpty
    def put(k: K, v: V): Store[K, V] = (k, v) :: s.remove(k)
    def get(k: K): Option[V] = s.find(_._1 == k).map(_._2)
    def remove(k: K): Store[K, V] = s.filterNot(_._1 == k)

