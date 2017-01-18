package sc.teams

import battlecode.common.GameActionException

/**
  * Base trait for scala.teams.
  */
trait RobotTeam {
  @throws(classOf[GameActionException])
  def run(): Unit
}
