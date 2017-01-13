package teams

import battlecode.common.RobotType._
import battlecode.common.{GameActionException, RobotController}
import units._

/**
  * Default team.
  */
class ShadowScoutTeam extends RobotTeam {
  @throws(classOf[GameActionException])
  override def run(implicit rc: RobotController): Unit =
    rc.getType match {
      case ARCHON => new DefaultArchon().run()
      case GARDENER => new ShadowGardener().run()
      case SOLDIER => new DefaultSoldier().run()
      case LUMBERJACK => new DefaultLumberjack().run()
      case SCOUT => new ShadowScout().run()
      case TANK => new DefaultTank().run()
    }
}
