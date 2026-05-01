package it.unibo.moduleType

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

trait KVStoreSpec(using storeModule: KVStore) extends AnyWordSpec with Matchers:

  import storeModule.*

  private val s0 = Store[String, Int]()
  val k1 = "i1"
  val v1 = 1
  val v2 = 2

  "A Store" when :
    "created" should :
      "be empty" in :
        s0.isEmpty should be(true)
    "adding an element" should :
      "return a new Store with the element" in :
        val s1 = s0.put(k1, v1)
        s1.get(k1) should be(Some(v1))
      "return a new Store with the overridden element if already present" in :
        val s1 = s0.put(k1, v1)
        val s2 = s1.put(k1, v2)
        s2.get(k1) should be(Some(v2))
    "removing an element" should :
      "return a new Store without the element" in :
        val s1 = s0.put(k1, v1)
        val s2 = s1.remove(k1)
        s2.get(k1) should be(None)
