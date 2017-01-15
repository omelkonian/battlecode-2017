package units

import java.lang.Math
import javax.print.attribute.standard.Destination
import battlecode.common._
import battlecode.common.RobotType._
import com.sun.xml.internal.ws.handler.HandlerProcessor
import utils.Movement._
import scala.collection.immutable.HashSet


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




      rc.broadcast(0,scanQuarter(0, 0, rc));
      rc.broadcast(1,scanQuarter(0, 1, rc));
      rc.broadcast(2,scanQuarter(1, 1, rc));
      rc.broadcast(3,scanQuarter(1, 0, rc));

      // Clock.yield() makes the robot wait until the next turn, then it will perform this loop again
      Clock.`yield`()

    } catch {
      case e: Exception =>
        System.out.println("ShadowScout Exception")
        e.printStackTrace()
    }
  }


  def scanQuarter(quarterX: Float, quarterY: Float, rc: RobotController): Int = {
    val enemy: Team = rc.getTeam.opponent()
    var quarterBottomLeft: MapLocation = new MapLocation((quarterX * (GameConstants.MAP_MAX_WIDTH / 2)), quarterY * (GameConstants.MAP_MAX_HEIGHT / 2));
    var quarterBottomRight: MapLocation= new MapLocation((quarterX*(GameConstants.MAP_MAX_WIDTH/2)+(GameConstants.MAP_MAX_WIDTH/2)),quarterY*(GameConstants.MAP_MAX_HEIGHT/2));
    var quarterTopLeft: MapLocation= new MapLocation((quarterX*(GameConstants.MAP_MAX_WIDTH/2)),quarterY*(GameConstants.MAP_MAX_HEIGHT/2)+(GameConstants.MAP_MAX_HEIGHT/2));
    var quarterTopRight: MapLocation= new MapLocation((quarterX*(GameConstants.MAP_MAX_WIDTH/2)+(GameConstants.MAP_MAX_WIDTH/2)),quarterY*(GameConstants.MAP_MAX_HEIGHT/2)+(GameConstants.MAP_MAX_HEIGHT/2));

    var quarterEnemyId =Set();
    var robots: Array[RobotInfo];

    while(rc.getLocation.compareTo(quarterBottomLeft)!=0) {
      try {
        rc.move(quarterBottomLeft);
        //Find enemy robots
        robots = rc.senseNearbyRobots(SCOUT.sensorRadius, enemy)
        for(i <- 0 to robots.length-1) {
          quarterEnemyId += robots(i).getID().toString;
      }

      } catch {
        case e: GameActionException =>
          System.out.println("ShadowScout Exception")
          e.printStackTrace()
      }
    }

    quarterBottomLeft = new MapLocation(quarterBottomLeft.x,quarterBottomLeft.y + SCOUT.sensorRadius)


    while(rc.getLocation.y>=quarterTopLeft.y) {

      while(rc.getLocation.compareTo(quarterBottomRight)!=0) {

        try {
          rc.move(quarterBottomRight);
          //Find enemy robots
          robots = rc.senseNearbyRobots(SCOUT.sensorRadius, enemy)
          for(i <- 0 to robots.length-1) {
            quarterEnemyId += robots(i).getID().toString;
          }
        } catch {
          case e: GameActionException =>
            System.out.println("ShadowScout Exception")
            e.printStackTrace()
        }
      }

      quarterBottomRight = new MapLocation(quarterBottomRight.x,quarterBottomRight.y + SCOUT.sensorRadius)

      try {
        rc.move(quarterBottomRight);
        //Find enemy robots
        robots = rc.senseNearbyRobots(SCOUT.sensorRadius, enemy)
        for(i <- 0 to robots.length-1) {
          quarterEnemyId += robots(i).getID().toString;
        }
      } catch {
        case e: GameActionException =>
          System.out.println("ShadowScout Exception")
          e.printStackTrace()
      }

      while(rc.getLocation.compareTo(quarterBottomLeft)!=0) {
        try {
          rc.move(quarterBottomLeft);
          //Find enemy robots
          robots = rc.senseNearbyRobots(SCOUT.sensorRadius, enemy)
          for(i <- 0 to robots.length-1) {
            quarterEnemyId += robots(i).getID().toString;
          }
        } catch {
          case e: GameActionException =>
            System.out.println("ShadowScout Exception")
            e.printStackTrace()
        }
      }

      try {
        rc.move(quarterBottomLeft);
        //Find enemy robots
        robots = rc.senseNearbyRobots(SCOUT.sensorRadius, enemy)
        for(i <- 0 to robots.length-1) {
          quarterEnemyId += robots(i).getID().toString;
        }
      } catch {
        case e: GameActionException =>
          System.out.println("ShadowScout Exception")
          e.printStackTrace()
      }
      rc.move(quarterBottomLeft);//Find enemy robots
      robots = rc.senseNearbyRobots(SCOUT.sensorRadius, enemy)
      for(i <- 0 to robots.length-1) {
        quarterEnemyId += robots(i).getID().toString;
      }
    }
    return quarterEnemyId.size;
  }
}