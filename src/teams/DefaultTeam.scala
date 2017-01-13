package teams
import battlecode.common.GameActionException
import battlecode.common.RobotType._
import units._
import utils.Current

/**
  * Default team.
  */
class DefaultTeam extends RobotTeam {
  @throws(classOf[GameActionException])
  override def run(): Unit =
    Current.I.getType match {
      case ARCHON => new DefaultArchon().run()
      case GARDENER => new DefaultGardener().run()
      case SOLDIER => new DefaultSoldier().run()
      case LUMBERJACK => new DefaultLumberjack().run()
      case _ =>
    }
}
