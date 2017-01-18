package sc.teams

import battlecode.common.RobotType._
import sc.units.ShadowScout

class ShadowScoutTeam extends SingleRobotTeam(SCOUT) {
  override def runSingle(): Unit = {
    new ShadowScout().run()
  }
}
