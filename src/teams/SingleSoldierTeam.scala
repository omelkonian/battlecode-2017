package teams
import battlecode.common.RobotType.SOLDIER
import units.DefaultSoldier


class SingleSoldierTeam extends SingleRobotTeam(SOLDIER) {
  override def runSingle(): Unit =
    new DefaultSoldier().run()
}
