package units

import battlecode.common._
import battlecode.common.RobotType._
import utils.Movement.{tryMove, randomDirection}

/**
  * Default gardener unit.
  */
class DefaultGardener extends RobotUnit {

  @throws(classOf[GameActionException])
  override def runTurn()(implicit rc: RobotController): Unit = {
    try {
      // Listen for home archon's location
      val xPos: Int = rc.readBroadcast(0)
      val yPos: Int = rc.readBroadcast(1)
      val archonLoc: MapLocation = new MapLocation(xPos,yPos)

      // Generate a random direction
      val dir: Direction = randomDirection()

      // Randomly attempt to build a soldier or lumberjack in this direction
      if (rc.canBuildRobot(SOLDIER, dir) && Math.random() < .01) {
        rc.buildRobot(SOLDIER, dir)
      } else if (rc.canBuildRobot(LUMBERJACK, dir) && Math.random() < .01 && rc.isBuildReady) {
        rc.buildRobot(LUMBERJACK, dir)
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
