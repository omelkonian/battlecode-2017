package sc.teams

import battlecode.common.RobotType.GARDENER
import sc.units.Farmer


class SingleFarmerTeam extends SingleRobotTeam(GARDENER) {
  override def runSingle(): Unit =
    new Farmer().run()
}
