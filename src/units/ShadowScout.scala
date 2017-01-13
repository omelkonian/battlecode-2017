package units

import java.lang.Math

import battlecode.common._
import battlecode.common.RobotType._
import utils.Movement._

/**
  * Default scout unit.
  */

class ShadowScout extends RobotUnit {

  private var lastDirection = 0.0;



  @throws(classOf[GameActionException])
  override def run()(implicit rc: RobotController): Unit = {
    while (true)
      runStep()
  }

  @throws(classOf[GameActionException])
  override def runStep()(implicit rc: RobotController): Unit = {
    try {

      val myLocation: MapLocation = rc.getLocation;

      val enemy: Team = rc.getTeam.opponent()
      //Find enemy robots
      val robots: Array[RobotInfo] = rc.senseNearbyRobots(SCOUT.sensorRadius, enemy)

      //Broadcast the first of them
      //Didn't broadcast because of cpu problems on this.
     /* rc.broadcast(2, robots(0).location.x.toInt);
      rc.broadcast(3, robots(0).location.y.toInt);*/



      //Go one level inside in the circle
      if(lastDirection%2*Math.PI==0) {
        lastDirection+=Math.PI/2;
        tryMove(new Direction(lastDirection.toFloat));
        lastDirection-=Math.PI/2;
      }

      //Circular movement one the grid
      if (rc.canMove(new Direction(lastDirection.toFloat))) {
        tryMove(new Direction(lastDirection.toFloat));
      } else {
        lastDirection+=Math.PI/2;
        if (rc.canMove(new Direction(lastDirection.toFloat))) {
          tryMove(new Direction(lastDirection.toFloat));
        } else {
          lastDirection+=Math.PI/2;
          tryMove(new Direction(lastDirection.toFloat));
        }
      }

      // Clock.yield() makes the robot wait until the next turn, then it will perform this loop again
      Clock.`yield`()

    } catch {
      case e: Exception =>
        System.out.println("ShadowScout Exception")
        e.printStackTrace()
    }
  }

}
