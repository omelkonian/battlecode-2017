package teams

import battlecode.common.RobotType._
import units._

class ShadowScoutTeam extends SingleRobotTeam(SCOUT) {
  override def runSingle(): Unit = {
    new ShadowScout().run()
  }
}
