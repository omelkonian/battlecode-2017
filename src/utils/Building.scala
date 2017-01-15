package utils

import battlecode.common.{Direction, RobotType}
import battlecode.common.RobotType.{GARDENER, ARCHON}
import utils.Current.{I, AmI}
import utils.Execution.waitAndThen
import utils.Movement.randomDirection

/**
  * Building utilities.
  */
object Building {

  /**
    * Skips turns until it can build a robot and then builds it.
    * @param robotType the robot to build
    * @param dir the direction in which to build
    * @param vary alter direction for variance on each try
    */
  def waitBuild(robotType: RobotType,
                dir: Direction = randomDirection(),
                vary: Direction => Direction = _ => randomDirection()): Unit = {
    robotType match {
      case GARDENER =>
        assert(AmI(ARCHON))
        waitAndThen(
          () => I canHireGardener dir,
          () => I hireGardener dir
        )
/*        waitAndThen[Direction](
          condition = I canHireGardener _,
          action = I hireGardener _,
          variance = vary,
          init = dir
        )*/
      case _ =>
        assert(AmI(GARDENER))
        waitAndThen(
          () => I canBuildRobot (robotType, dir),
          () => I buildRobot (robotType, dir)
        )
/*        waitAndThen[Direction](
          condition = I canBuildRobot (robotType, _),
          action = I buildRobot (robotType, _),
          variance = vary,
          init = dir
        )*/

    }
  }
}
