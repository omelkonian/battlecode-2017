package teams
import battlecode.common.GameActionException

/**
  * Base trait for teams.
  */
trait RobotTeam {
  @throws(classOf[GameActionException])
  def run(): Unit
}
