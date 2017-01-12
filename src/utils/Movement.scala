package utils

import battlecode.common.{Direction, GameActionException, RobotController}

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
    * Attempts to move in a given direction, while avoiding small obstacles directly in the path.
    *
    * @param dir The intended direction of movement
    * @return true if a move was performed
    * @throws GameActionException
    */
  @throws(classOf[GameActionException])
  def tryMove(dir: Direction)(implicit rc: RobotController): Boolean =
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
  def tryMove(dir: Direction, degreeOffset: Float, checksPerSide: Int)(implicit rc: RobotController): Boolean = {

    // First, try intended direction
    if (rc.canMove(dir)) {
      rc.move(dir)
      return true
    }

    // Now try a bunch of similar angles
    var currentCheck: Int = 1

    while(currentCheck <= checksPerSide) {
      // Try the offset of the left side
      if(rc.canMove(dir.rotateLeftDegrees(degreeOffset*currentCheck))) {
        rc.move(dir.rotateLeftDegrees(degreeOffset*currentCheck))
        return true
      }
      // Try the offset on the right side
      if(rc.canMove(dir.rotateRightDegrees(degreeOffset*currentCheck))) {
        rc.move(dir.rotateRightDegrees(degreeOffset*currentCheck))
        return true
      }
      // No move performed, try slightly further
      currentCheck += 1
    }

    // A move never happened, so return false.
    false
  }
}
