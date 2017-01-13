package units
import battlecode.common._
import battlecode.common.RobotType._
import utils.Movement.{tryMove, randomDirection}
import utils.Current.I

/**
  * Default gardener unit.
  */
class DefaultGardener extends RobotUnit {

  @throws(classOf[GameActionException])
  override def runTurn(): Unit = {
    try {
      // Listen for home archon's location
      val xPos: Int = I.readBroadcast(0)
      val yPos: Int = I.readBroadcast(1)
      val archonLoc: MapLocation = new MapLocation(xPos,yPos)

      // Generate a random direction
      val dir: Direction = randomDirection()

      // Randomly attempt to build a soldier or lumberjack in this direction
      if (I.canBuildRobot(SOLDIER, dir) && Math.random() < .01) {
        I.buildRobot(SOLDIER, dir)
      } else if (I.canBuildRobot(LUMBERJACK, dir) && Math.random() < .01 && I.isBuildReady) {
        I.buildRobot(LUMBERJACK, dir)
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
