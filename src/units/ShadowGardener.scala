package units

import battlecode.common.RobotType._
import battlecode.common._
import utils.Movement.{randomDirection, tryMove}

/**
  * Default gardener unit.
  */
class ShadowGardener extends RobotUnit {

  @throws(classOf[GameActionException])
  override def runStep()(implicit rc: RobotController): Unit = {
    try {
      // Listen for home archon's location
      val xPos: Int = rc.readBroadcast(0)
      val yPos: Int = rc.readBroadcast(1)
      val archonLoc: MapLocation = new MapLocation(xPos,yPos)

      // Generate a random direction
      val dir: Direction = randomDirection()

      // Randomly attempt to build a soldier or scout in this direction
      if (rc.canBuildRobot(SOLDIER, dir) && Math.random() < .01) {
        rc.buildRobot(SOLDIER, dir)
      } else if(rc.canBuildRobot(SCOUT, dir) && Math.random() < .01) {
        rc.buildRobot(SCOUT, dir);
      }

      // Move randomly
      tryMove(randomDirection())

      // Clock.yield() makes the robot wait until the next turn, then it will perform this loop again
      Clock.`yield`()

    } catch {
      case e: Exception =>
        System.out.println("Gardener Exception")
        e.printStackTrace()
    }
  }
}
