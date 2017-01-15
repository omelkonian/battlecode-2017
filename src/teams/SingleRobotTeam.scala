package teams
import battlecode.common.{GameActionException, RobotType}
import battlecode.common.RobotType.{ARCHON, GARDENER}
import utils.Current.I
import utils.Building.waitBuild

/**
  * Base class for single robot teams (for testing).
  */
abstract class SingleRobotTeam(robotType: RobotType) extends RobotTeam {

  @throws(classOf[GameActionException])
  override def run(): Unit = {
    robotType match {
      // Archon runs
      case ARCHON =>
        runSingle()
      // Archon spawns Gardener and dies -> Gardener runs
      case GARDENER =>
        I.getType match {
          case ARCHON => archonSpawnSingleGardener()
          case GARDENER => runSingle()
          case _ =>
        }
      // Archon spawns Gardener and dies -> Gardener spawns Robot and dies -> Robot runs
      case _ =>
        I.getType match {
          case ARCHON => archonSpawnSingleGardener()
          case GARDENER => waitBuild(robotType)
          case `robotType` => runSingle()
          case _ =>
        }
    }
  }

  def runSingle(): Unit

  private def archonSpawnSingleGardener(): Unit =
    if (I.readBroadcast(0) == 0) {
      // Set flag to kill next archons
      I.broadcast(0, 1)
      // Construct a gardener and die
      waitBuild(GARDENER)
    }
}
