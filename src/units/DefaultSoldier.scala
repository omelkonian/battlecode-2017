package units
import battlecode.common._
import utils.Movement.{randomDirection, tryMove}
import utils.Current.I

/**
  * Default soldier unit.
  */
class DefaultSoldier extends RobotUnit {

  var enemy: Team = _

  @throws(classOf[GameActionException])
  override def run(): Unit = {
    enemy = I.getTeam.opponent()
    while (true)
      runStep()
  }

  @throws(classOf[GameActionException])
  def runStep(): Unit = {
    val enemy: Team = I.getTeam.opponent()
      try {
        // See if there are any nearby enemy robots
        val robots: Array[RobotInfo] = I.senseNearbyRobots(-1, enemy)

        // If there are some...
        if (robots.length > 0)
          // And we have enough bullets, and haven't attacked yet this turn...
          if (I.canFireSingleShot)
            // ...Then fire a bullet in the direction of the enemy.
            I.fireSingleShot(I.getLocation.directionTo(robots(0).location))

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
