package sc.utils

import battlecode.common.{BodyInfo, Clock, Direction, MapLocation}
import j.utils.Constants._
import j.utils.Current._
import sc.utils.Geometry.Rectangle

import sc.utils.Sensing._
import sc.utils.Geometry._
import sc.utils.Movement._

/**
  * Pathfinding utilities.
  */
object Pathfinding {

  type Obstacle = (MapLocation, Float)

  /**
    * Directs the robot away from obstacles.
    * @return the most "lonely" direction
    */
  def moveAwayFromObstacles(): Direction = {
    val myLoc = Here
    if (I.hasMoved)
      Clock.`yield`()
    // Sense obstacles
    val obstacles = senseNearbyObstacles() map (_.getLocation)
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

  /**
    *             ---- TODO ----
    * Pathfinding towards a specific direction.
    * @param dir the direction to follow
    * @param action the action to take after each move
    * @param termination the termination criterion
    */
  def pathfindTowards(dir: Direction,
                      action: () => Unit,
                      termination: () => Boolean): Unit = {
    // Save initial location for later reference
    val initLoc = Here
    val currentDir = dir

    while (!termination()) {
      // Correction of direction

//      val nextPos: Circle = I translate (dir, I.strideRadius)

      // Future movement area
      val movement: Rectangle = Rectangle(
        I pointAt dir.rotateLeftDegrees(PI/2).radians,
        Here.add(dir, I.sensorRadius).add(dir rotateRightRads (PI/2), I.radius)
      )

      // Obstacle on the way
      val blockingObstacle: Option[BodyInfo] =
        I.senseNearbyObstacles()
         .find(movement isInside _)

      blockingObstacle match {
        case Some(obstacle) =>
          // Steer as much as necessary
//          val steerSide: Position =
        case None =>
      }

      // Make movement
      tryMove(currentDir)

      // Finally, run provided callback
      action()
    }
  }

  // Implicit class
  implicit class PathfindingRobot(erc: EnrichedRC) {
    def moveAwayFromObstacles: () => Direction = Pathfinding.moveAwayFromObstacles
    def pathFindTowards: (Direction, () => Unit, () => Boolean) => Unit = Pathfinding.pathfindTowards
  }
}
