/** This file contains a series of test stressing Scala Collection Framework. */
package it.unibo.collections

@main def testIterable(): Unit =
  // Iterable -> Seq -> LinearSeq -> List
  val it = Iterable(1, 2, 3, 4)
  assert:
    it.isInstanceOf[List[Int]]

@main def testSequences(): Unit =
  // Seq -> LinearSeq -> List
  val seq = Seq(1, 2, 3, 4)
  assert:
    seq.isInstanceOf[List[Int]]

@main def testSets(): Unit =
  // Set -> HashSet (?) (depends on its cardinality)
  //     -> Set1
  //     -> Set2
  //     -> Set3
  //     -> Set4
  val set = Set(1, 1, 5, 3, 2, 4)
  assert:
    set.isInstanceOf[scala.collection.immutable.HashSet[Int]]

@main def testMaps(): Unit =
  // Map -> HashMap (?) (depends on its cardinality)
  //     -> Map1
  //     -> Map2
  //     -> Map3
  //     -> Map4
  val map = Map[String, Int](
    "one" -> 1,
    "two" -> 2,
    "three" -> 3,
    "four" -> 4,
    "five" -> 5
  )
  assert:
    map.isInstanceOf[scala.collection.immutable.HashMap[String, Int]]

@main def testMutableCollections(): Unit = ???
