package units

import battlecode.common._
import battlecode.common.RobotType._
import battlecode.common.GameConstants.LUMBERJACK_STRIKE_RADIUS
import utils.Movement.{randomDirection, tryMove}

/**
  * Default lumberjack unit.
  */
class DefaultLumberjack extends RobotUnit {

  @throws(classOf[GameActionException])
  override def run()(implicit rc: RobotController): Unit = {
    val enemy: Team = rc.getTeam.opponent()
    while (true)
      runTurn(enemy)
  }

  @throws(classOf[GameActionException])
  def runTurn(enemy: Team)(implicit rc: RobotController): Unit = {
    try {
      // See if there are any enemy robots within striking range (distance 1 from lumberjack's radius)
      var robots: Array[RobotInfo] = rc.senseNearbyRobots(LUMBERJACK.bodyRadius + LUMBERJACK_STRIKE_RADIUS, enemy)

      if(robots.length > 0 && !rc.hasAttacked)
        // Use strike() to hit all nearby robots!
        rc.strike()
      else {
        // No close robots, so search for robots within sight radius
        robots = rc.senseNearbyRobots(-1, enemy)

        // If there is a robot, move towards it
        if(robots.length > 0) {
          val myLocation: MapLocation = rc.getLocation
          val enemyLocation: MapLocation = robots(0).getLocation
          val toEnemy: Direction = myLocation.directionTo(enemyLocation)
          tryMove(toEnemy)
        } else
          // Move Randomly
          tryMove(randomDirection())
      }

      // Clock.yield() makes the robot wait until the next turn, then it will perform this loop again
      Clock.`yield`()

    } catch {
      case e: Exception =>
        System.out.println("Lumberjack Exception")
        e.printStackTrace()
    }
  }
}
