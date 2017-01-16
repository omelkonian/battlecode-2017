package utils
import battlecode.common.{BodyInfo, Direction, MapLocation}

import scala.collection.mutable.ListBuffer
import utils.Constants.PI

/**
  * Geometric utilities.
  */
object Geometry {
  /*
    ENTITIES
   */

  type Point = MapLocation
  type Angle = Float // in radians

  object Position extends Enumeration {
    type Position = Value
    val TOP_LEFT, TOP, TOP_RIGHT, BOTTOM_LEFT, BOTTOM, BOTTOM_RIGHT, LEFT, RIGHT, INSIDE = Value
  }
  import Position._

  case class Line(p1: Point, p2: Point) {
//    def positionOf(point: Point): Position =
  }

  case class Circle(center: Point, radius: Float) {
    def pointAt(angle: Angle): Point =
      center add new Direction(angle)

    def top: Point = pointAt(PI / 2)
    def bottom: Point = pointAt(3 * PI/2)
    def left: Point = pointAt(PI)
    def right: Point = pointAt(0)

    def translate(dx: Float, dy: Float): Circle =
      Circle(center translate (dx, dy), radius)
    def translate(dir: Direction, distance: Float): Circle =
      Circle(center add (dir, distance), radius)
  }

  case class Rectangle(topLeft: Point, bottomRight: Point) {//, dir: Direction) {
    val topRight: Point = topLeft translate (bottomRight.x, 0f)
    val bottomLeft: Point = bottomRight translate (-topLeft.x, 0f)

    def width: Float = bottomRight.x - topLeft.x
    def height: Float = topLeft.y - bottomRight.y

    def translate(dx: Float, dy: Float): Rectangle =
      Rectangle(topLeft translate (dx, dy), bottomRight translate (dx, dy))

    def isInside(point: Point): Boolean =
      topLeft.x <= point.x && point.x <= bottomLeft.x &&
        bottomLeft.y <= point.y && point.y <= topLeft.y

    def isInside(circle: Circle): Boolean =
      distanceFrom(circle.center) match {
        case 0 => true
        case distance => distance > circle.radius
      }

    def positionOfPoint(point: Point): Position =
      if (this isInside point)
        INSIDE
      else
      if (point.x < topLeft.x) {
        if (point.y > topLeft.y) TOP_LEFT
        else if (point.y > bottomLeft.y) LEFT
        else BOTTOM_LEFT
      }
      else if (point.x > bottomLeft.x) {
        if (point.y > topLeft.y) TOP_RIGHT
        else if (point.y > bottomLeft.y) RIGHT
        else BOTTOM_RIGHT
      }
      else {
        if (point.y > topLeft.y) TOP
        else BOTTOM
      }

    def distanceFrom(point: Point): Float =
      positionOfPoint(point) match {
        case INSIDE => 0
        case TOP => point.y - topLeft.y
        case BOTTOM => bottomRight.y - point.y
        case LEFT => topLeft.x - point.x
        case RIGHT => point.x - bottomRight.x
        case TOP_LEFT => point distanceTo topLeft
        case TOP_RIGHT => point distanceTo topRight
        case BOTTOM_LEFT => point distanceTo bottomLeft
        case BOTTOM_RIGHT => point distanceTo bottomRight
      }
  }


  /*
    FUNCTIONS
   */

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

  /*
    IMPLICITS
   */

  implicit class GeometryRobot(enrichedRC: EnrichedRC)
    extends Circle(enrichedRC.getLocation, enrichedRC.radius)
  implicit class GeometryBody(body: BodyInfo)
    extends Circle(body.getLocation, body.radius)

}
