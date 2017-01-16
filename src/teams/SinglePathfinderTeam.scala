package teams
import battlecode.common.RobotType.GARDENER
import units.Pathfinder


class SinglePathfinderTeam extends SingleRobotTeam(GARDENER) {
  override def runSingle(): Unit =
    new Pathfinder().run()
}
