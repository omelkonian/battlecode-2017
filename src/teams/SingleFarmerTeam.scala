package teams
import battlecode.common.RobotType.GARDENER
import units.Farmer


class SingleFarmerTeam extends SingleRobotTeam(GARDENER) {
  override def runSingle(): Unit =
    new Farmer().run()
}
