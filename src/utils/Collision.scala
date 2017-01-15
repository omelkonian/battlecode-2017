package utils
import battlecode.common.{BulletInfo, Direction, MapLocation}
import utils.Current.I

/**
  * Collision utilities.
  */
object Collision {

  /**
    * A slightly more complicated example function, this returns true if the given bullet is on a collision
    * course with the current robot. Doesn't take into account objects between the bullet and this robot.
    *
    * @param bullet The bullet in question
    * @return True if the line of the bullet's path intersects with this robot's current position.
    */
  def willCollideWithMe(bullet: BulletInfo): Boolean = {
    val myLocation: MapLocation = I.getLocation

    // Get relevant bullet information
    val propagationDirection: Direction = bullet.dir
    val bulletLocation: MapLocation = bullet.location

    // Calculate bullet relations to this robot
    val directionToRobot: Direction = bulletLocation.directionTo(myLocation)
    val distToRobot: Float = bulletLocation.distanceTo(myLocation)
    val theta: Float = propagationDirection.radiansBetween(directionToRobot)

    // If theta > 90 degrees, then the bullet is traveling away from us and we can break early
    if (Math.abs(theta) > Math.PI/2)
      return false

    // distToRobot is our hypotenuse, theta is our angle, and we want to know this length of the opposite leg.
    // This is the distance of a line that goes from myLocation and intersects perpendicularly with propagationDirection.
    // This corresponds to the smallest radius circle centered at our location that would intersect with the
    // line that is the path of the bullet.
    val perpendicularDist: Float = math.abs(distToRobot * Math.sin(theta)).toFloat // soh cah toa :)

    perpendicularDist <= I.getType.bodyRadius
  }
}
