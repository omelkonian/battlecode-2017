package utils
import battlecode.common.MapLocation
import scala.collection.mutable.ListBuffer

/**
  * Geometric utilities.
  */
object Geometry {
  type Point = MapLocation

  /**
    * @return the middle point of two points
    */
  def midpoint(start: Point, end: Point): Point =
    new Point((start.x + end.x) / 2, (start.y + end.y) / 2)

  /**
    * Computes the convex hull of a set of points.
    * @param points initial set of points
    * @return the vertices of the convex hull
    */
  def convexHull(points: Seq[Point]): Seq[Point] = {
    def halfHull(_points: Seq[Point]) = {
      val upper = new ListBuffer[Point]()
      for (p <- _points) {
        while (upper.size >= 2 && leftTurn(p, upper(0), upper(1)))
          upper.remove(0)
        upper.prepend(p)
      }
      upper
    }

    def leftTurn(p1: Point, p2: Point, p3: Point) = {
      val slope = (p2.x - p1.x) * (p3.y - p1.y) - (p2.y - p1.y) * (p3.x - p1.x)
      val collinear = math.abs(slope) <= 1e-9
      val leftTurn = slope < 0
      collinear || leftTurn
    }
    val _points = points.sortBy(_.x)
    val upper = halfHull(_points)
    val lower = halfHull(_points.reverse)
    upper.remove(0)
    lower.remove(0)
    upper ++: lower
  }


}
