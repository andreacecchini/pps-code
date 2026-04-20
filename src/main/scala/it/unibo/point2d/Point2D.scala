package it.unibo.point2d


/** Product Type
 * |Point2D| = |Double| * |Double|
 * */
case class Point2D(x: Double, y: Double)

/** using pattern matching to declaratively manipulate a point */
def rotate(p: Point2D): Point2D = p match
  case Point2D(x, y) => Point2D(y, -x)

/** matching over couple ([[p1]], [[p2]]) */
def sum(p1: Point2D, p2: Point2D): Point2D = (p1, p2) match
  case (Point2D(x1, y1), Point2D(x2, y2)) => Point2D(x1 + x2, y1 + y2)

/** partial matching */
def getX(p: Point2D): Double = p match
  case Point2D(x, _) => x

/** "fluent" setter */
def setX(p: Point2D, x: Double): Point2D = p match
  case Point2D(_, y) => Point2D(x, y)

@main def main(): Unit =
  val p = Point2D(1.0, 0.0)
  println(p) // Point2D(1.0, 0.0)
  val rotated = rotate(p)
  println(rotated) // Point2D(0.0, -1.0)
  val p2 = sum(p, rotate(p))
  println(p2) // Point2D(1.0, -1.0)
  val x = getX(p)
  println(x) // 1.0
  val p3 = setX(p, 2.0)
  println(p3) // Point2D(2.0, 0.0)
