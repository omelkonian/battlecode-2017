package units

import battlecode.common.RobotType._
import battlecode.common._
import utils.Movement.{randomDirection, tryMove}

/**
  * Default archon unit.
  */
class ShadowArchon extends RobotUnit {

  @throws(classOf[GameActionException])
  override def runStep()(implicit rc: RobotController): Unit = {
    try {
      // Generate a random direction
      val dir: Direction = randomDirection()

      // Randomly attempt to build a gardener in this direction
      if (rc.canHireGardener(dir) && Math.random() < .01)
        rc.hireGardener(dir)


      var quarterEnemyCount: Array[Int]= Array(4);

      var minEnemyCount = 0;
      var minEnemyCountQuarter = -1;
      for(i <-0 to 3) {
        if(minEnemyCount>=rc.readBroadcast(i)) {
          minEnemyCountQuarter = i;
        }
      }
      goToQuarter(minEnemyCountQuarter, rc);
     val firstQuarterEnemyCount = rc.readBroadcast(0)
      rc.readBroadcast(1)
      rc.readBroadcast(2)
      rc.readBroadcast(3)
      // Move randomly
      tryMove(randomDirection())

      // Broadcast archon's location for other robots on the team to know
      val myLocation: MapLocation = rc.getLocation
      rc.broadcast(0, myLocation.x.toInt)
      rc.broadcast(1, myLocation.y.toInt)

      // Clock.yield() makes the robot wait until the next turn, then it will perform this loop again
      Clock.`yield`()

    } catch {
      case e: Exception =>
        System.out.println("Archon Exception")
        e.printStackTrace()
    }
  }

  def goToQuarter(quarterNumber: Int, rc: RobotController): Unit = {

    var quarterX: Int =0;
    var quarterY: Int =0;

    if(quarterNumber == 0) {
      quarterX= 0;
      quarterY= 0;
    } else if(quarterNumber == 1) {
      quarterX= 0;
      quarterY= 1;
    } else if(quarterNumber == 2) {
      quarterX= 1;
      quarterY= 1;
    } else if(quarterNumber == 3) {
      quarterX= 1;
      quarterY= 0;
    } else {
      return
    }

    //Maybe better to go at the center of the quarter
    var quarterBottomLeft: MapLocation = new MapLocation((quarterX * (GameConstants.MAP_MAX_WIDTH / 2)), quarterY * (GameConstants.MAP_MAX_HEIGHT / 2));


    try {
      rc.move(quarterBottomLeft);
    } catch {
      case e: GameActionException =>
        System.out.println("ShadowScout Exception")
        e.printStackTrace()
    }
  }
}
