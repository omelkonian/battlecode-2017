package teams

import battlecode.common.{GameActionException, RobotController}

/**
  * Base trait for teams.
  */
trait RobotTeam {
  @throws(classOf[GameActionException])
  def run(implicit rc: RobotController): Unit
}
