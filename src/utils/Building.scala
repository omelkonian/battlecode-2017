package utils

import battlecode.common.{Direction, RobotType}
import battlecode.common.RobotType.{GARDENER, ARCHON}
import utils.Current.{I, AmI}
import utils.Execution._
import utils.Movement.randomDirection
import java.lang.RuntimeException

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
      case ARCHON => throw new RuntimeException("Cannot build Archon")
      case GARDENER => assert(AmI(ARCHON))
      case _ => assert(AmI(GARDENER))
    }
    waitAndThen2[Direction](
      condition = I canBuildRobot (robotType, _),
      action = I buildRobot (robotType, _),
      variance = vary,
      init = dir
    )
  }

}
