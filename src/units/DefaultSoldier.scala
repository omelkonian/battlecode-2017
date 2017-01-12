package units
import battlecode.common._
import utils.Movement.{randomDirection, tryMove}

/**
  * Default soldier unit.
  */
class DefaultSoldier extends RobotUnit {

  @throws(classOf[GameActionException])
  override def run()(implicit rc: RobotController): Unit =
    while (true)
      runTurn(rc.getTeam.opponent())

  @throws(classOf[GameActionException])
  def runTurn(enemy: Team)(implicit rc: RobotController): Unit = {
    val enemy: Team = rc.getTeam.opponent()
      try {
        // See if there are any nearby enemy robots
        val robots: Array[RobotInfo] = rc.senseNearbyRobots(-1, enemy)

        // If there are some...
        if (robots.length > 0)
          // And we have enough bullets, and haven't attacked yet this turn...
          if (rc.canFireSingleShot)
            // ...Then fire a bullet in the direction of the enemy.
            rc.fireSingleShot(rc.getLocation.directionTo(robots(0).location))

        // Move randomly
        tryMove(randomDirection())

        // Clock.yield() makes the robot wait until the next turn, then it will perform this loop again
        Clock.`yield`()

      } catch {
        case e: Exception =>
          System.out.println("Soldier Exception")
          e.printStackTrace()
      }
    }
}
