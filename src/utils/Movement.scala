package utils
import battlecode.common.{Clock, Direction, GameActionException, MapLocation}
import utils.Execution.waitAndThen
import utils.Current.I

/**
  * Movement utilities.
  */
object Movement {

  /**
    * @return a random Direction
    */
  def randomDirection(): Direction =
    new Direction((math.random * 2.0 * math.Pi).toFloat)

  /**
    * Wanders around with a specific strategy until a condition is met.
    * @param condition the condition to satisfy
    * @param nextDirection wandering strategy
    */
  def wanderUntil(condition: () => Boolean, nextDirection: () => Direction): Unit =
    while (!condition()) {
      tryMove(nextDirection())
      Clock.`yield`()
    }


  /**
    * Moves to a single direction, skipping turns if necessary.
    * @param dir the direction to move
    */
  def waitMove(dir: Direction, distance: Float = -1): Unit =
    if (distance > 1) { // multiple strides
      val moves = (distance / I.getType.strideRadius).toInt
      val rest = distance - moves
      for (_ <- 0 until moves)
        waitMove(dir)
      if (rest > 0)
        waitMove(dir, distance - moves)
    }
    else if (distance != 0)// single stride
      waitAndThen(
        () => !I.hasMoved && I.canMove(dir, distance),
        () => I.move(dir, if (distance != -1) distance else I.getType.strideRadius)
      )
/*      waitAndThen[Unit](
        condition = _ => !I.hasMoved && I.canMove(dir, distance),
        action = _ => I.move(dir, if (distance != -1) distance else I.getType.strideRadius),
        variance = _ => (),
        init = ()
      )*/

  /**
    * Attempts to move in a given direction, while avoiding small obstacles directly in the path.
    *
    * @param dir The intended direction of movement
    * @return true if a move was performed
    * @throws GameActionException
    */
  @throws(classOf[GameActionException])
  def tryMove(dir: Direction): Boolean =
    tryMove(dir,20,3)

  /**
    * Attempts to move in a given direction, while avoiding small obstacles direction in the path.
    *
    * @param dir The intended direction of movement
    * @param degreeOffset Spacing between checked directions (degrees)
    * @param checksPerSide Number of extra directions checked on each side, if intended direction was unavailable
    * @return true if a move was performed
    * @throws GameActionException
    */
  @throws(classOf[GameActionException])
  def tryMove(dir: Direction, degreeOffset: Float, checksPerSide: Int): Boolean = {

    // First, try intended direction
    if (I.canMove(dir)) {
      I.move(dir)
      return true
    }

    // Now try a bunch of similar angles
    var currentCheck: Int = 1

    while(currentCheck <= checksPerSide) {
      // Try the offset of the left side
      if(I.canMove(dir.rotateLeftDegrees(degreeOffset*currentCheck))) {
        I.move(dir.rotateLeftDegrees(degreeOffset*currentCheck))
        return true
      }
      // Try the offset on the right side
      if(I.canMove(dir.rotateRightDegrees(degreeOffset*currentCheck))) {
        I.move(dir.rotateRightDegrees(degreeOffset*currentCheck))
        return true
      }
      // No move performed, try slightly further
      currentCheck += 1
    }

    // A move never happened, so return false.
    false
  }

  /**
    * Direction from current location to mapLocation
    * @param mapLocation
    * @return the direction
    */
  def curDirTo(mapLocation: MapLocation): Direction = {
    return I.getLocation.directionTo(mapLocation)
  }
}
