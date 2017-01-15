package teams
import battlecode.common.{Clock, GameActionException}

/**
  * Team that just keeps the archons idle.
  */
class IdleTeam extends RobotTeam {
  @throws(classOf[GameActionException])
  override def run(): Unit =
    while (true)
      Clock.`yield`()
}
