package units

import battlecode.common.{GameActionException, RobotController}

/**
  * Base trait for units.
  */
trait RobotUnit {

  @throws(classOf[GameActionException])
  def run()(implicit rc: RobotController): Unit =
    while (true)
      runStep()

  @throws(classOf[GameActionException])
  def runStep()(implicit rc: RobotController): Unit = ???
}
