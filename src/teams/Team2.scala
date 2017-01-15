package teams

import battlecode.common.RobotType._
import battlecode.common.{GameActionException, RobotController}
import units._

/**
  * Default team.
  */
class Team2 extends RobotTeam {
  @throws(classOf[GameActionException])
  override def run(implicit rc: RobotController): Unit =
    rc.getType match {
      case ARCHON => new DefaultArchon().run()
      case GARDENER => new StupidGardener().run()
      case SOLDIER => new DefaultSoldier().run()
      case LUMBERJACK => new DefaultLumberjack().run()
      case SCOUT => new DefaultScout().run()
      case TANK => new StupidTank().run()
    }
}
