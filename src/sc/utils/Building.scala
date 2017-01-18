package sc.utils

import battlecode.common.RobotType.{ARCHON, GARDENER}
import battlecode.common.{Direction, RobotType}
import j.utils.Current.{AmI, I}
import Movement._
import Execution._

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
    waitAndThen[Direction](
      condition = I canBuildRobot (robotType, _),
      action = I buildRobot (robotType, _),
      variance = vary,
      init = dir
    )
  }

  // Implicit class
  implicit class BuildingRobot(erc: EnrichedRC) {
    def waitBuild: (RobotType, Direction, (Direction) => Direction) => Unit = Building.waitBuild
  }
}
