package it.unibo.oop


@main def testCaseVsClass(): Unit =
  class Point2D(val x: Int, val y: Int)
  val origin: Point2D = Point2D(0, 0)
  println(origin)
  println:
    s"(${origin.x}, ${origin.y})"
  case class CasePoint2D(x: Int, y: Int)
  val origin2: CasePoint2D = CasePoint2D(0, 0)
  println(origin2)
  println:
    s"(${origin.x}, ${origin.y})"


