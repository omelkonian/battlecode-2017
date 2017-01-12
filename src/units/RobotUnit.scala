package units

import battlecode.common.{GameActionException, RobotController}

/**
  * Base trait for units.
  */
trait RobotUnit {

  @throws(classOf[GameActionException])
  def run()(implicit rc: RobotController): Unit =
    while (true)
      runTurn()

  @throws(classOf[GameActionException])
  def runTurn()(implicit rc: RobotController): Unit = ???
}
