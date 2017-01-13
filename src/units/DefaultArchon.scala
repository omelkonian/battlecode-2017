package units

import battlecode.common._
import utils.Movement.{randomDirection, tryMove}

/**
  * Default archon unit.
  */
class DefaultArchon extends RobotUnit {

  @throws(classOf[GameActionException])
  override def runStep()(implicit rc: RobotController): Unit = {
    try {
      // Generate a random direction
      val dir: Direction = randomDirection()

      // Randomly attempt to build a gardener in this direction
      if (rc.canHireGardener(dir) && Math.random() < .01)
        rc.hireGardener(dir)

      // Move randomly
      tryMove(randomDirection())

      // Broadcast archon's location for other robots on the team to know
      val myLocation: MapLocation = rc.getLocation
      rc.broadcast(0, myLocation.x.toInt)
      rc.broadcast(1, myLocation.y.toInt)

      // Clock.yield() makes the robot wait until the next turn, then it will perform this loop again
      Clock.`yield`()

    } catch {
      case e: Exception =>
        System.out.println("Archon Exception")
        e.printStackTrace()
    }
  }
}
