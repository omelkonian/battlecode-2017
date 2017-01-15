package utils

import battlecode.common.{Clock, Direction, MapLocation}
import utils.Current.{I, Here}
import utils.Sensing.senseNearbyObstacles
import utils.Geometry.{midpoint, convexHull}

/**
  * Pathfinding utilities.
  */
object Pathfinding {

  /**
    * Directs the robot away from obstacles.
    * @return the most "lonely" direction
    */
  def moveAwayFromObstacles(): Direction = {
    val myLoc = Here
    if (I.hasMoved)
      Clock.`yield`()
    // Sense obstacles
    val obstacles = senseNearbyObstacles().map(_.getLocation)
    // Compute convex hull of obstacles
    val convex: Seq[MapLocation] = convexHull(obstacles :+ myLoc)
    // Decide next direction, so as to avoid obstacles
    val index = convex indexOf myLoc
    val targetToAvoid =
      if (index != -1) // Outside convex hull
        midpoint(convex(index - 1), convex(index + 1))
      else // Inside convex hull
        convex.zip(convex.tail)
          .map { case (l1, l2) => (l1 distanceTo l2, midpoint(l1, l2)) }
          .maxBy(_._1)
          ._2
    // Move opposite from target-to-avoid
    myLoc.directionTo(targetToAvoid).opposite()
  }

}
