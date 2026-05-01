package it.unibo.opaque

object Sets:
  opaque type Set[A] = List[A]

  def fromList[A](l: List[A]): Set[A] = l

  extension [A](s: Set[A])
    def toList: List[A] = s.distinct
    def remove(el: A): Set[A] = s.filter(_ != el)
    infix def union(other: Set[A]): Set[A] = s.concat(other)
    infix def intersect(other: Set[A]): Set[A] = s match
      case h :: t if other.contains(h) => h :: t.intersect(other)
      case _ :: t => t.intersect(other)
      case Nil => Nil


@main def testMain(): Unit =
  import Sets.*

  val s1: Set[Int] = fromList(List(1, 2, 1, 2))
  println(s1.toList) // List(1, 2)
  val s2: Set[Int] = fromList(List(1, 3))
  val s3 = s1 union s2
  println(s3.toList) // List(1, 3, 1) in any order...
  val s4 = s1 intersect s2
  println(s4.toList) // List(1)
