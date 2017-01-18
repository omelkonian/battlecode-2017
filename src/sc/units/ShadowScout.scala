package sc.units

import battlecode.common.RobotType._
import battlecode.common._
import j.utils.Current.I
import sc.utils.EnrichedRC

/**
  * Default scout unit.
  */

class ShadowScout extends RobotUnit {

  @throws(classOf[GameActionException])
  override def runStep(): Unit = {
    try {

      I.broadcast(0,scanQuarter(0, 0, I))
      I.broadcast(1,scanQuarter(0, 1, I))
      I.broadcast(2,scanQuarter(1, 1, I))
      I.broadcast(3,scanQuarter(1, 0, I))

      // Clock.yield() makes the robot wait until the next turn, then it will perform this loop again
      Clock.`yield`()

    } catch {
      case e: Exception =>
        System.out.println("ShadowScout Exception")
        e.printStackTrace()
    }
  }


  def scanQuarter(quarterX: Float, quarterY: Float, rc: EnrichedRC): Int = {
    val enemy: Team = rc.getTeam.opponent()
    var quarterBottomLeft: MapLocation = new MapLocation((quarterX * (GameConstants.MAP_MAX_WIDTH / 2)), quarterY * (GameConstants.MAP_MAX_HEIGHT / 2));
    var quarterBottomRight: MapLocation= new MapLocation((quarterX*(GameConstants.MAP_MAX_WIDTH/2)+(GameConstants.MAP_MAX_WIDTH/2)),quarterY*(GameConstants.MAP_MAX_HEIGHT/2));
    var quarterTopLeft: MapLocation= new MapLocation((quarterX*(GameConstants.MAP_MAX_WIDTH/2)),quarterY*(GameConstants.MAP_MAX_HEIGHT/2)+(GameConstants.MAP_MAX_HEIGHT/2));
    var quarterTopRight: MapLocation= new MapLocation((quarterX*(GameConstants.MAP_MAX_WIDTH/2)+(GameConstants.MAP_MAX_WIDTH/2)),quarterY*(GameConstants.MAP_MAX_HEIGHT/2)+(GameConstants.MAP_MAX_HEIGHT/2));

    var quarterEnemyId = scala.collection.mutable.Set[String]()

    while(rc.getLocation.compareTo(quarterBottomLeft)!=0) {
      try {
        rc.move(quarterBottomLeft)
        //Find enemy robots
        val robots = rc.senseNearbyRobots(SCOUT.sensorRadius, enemy)
        for(i <- robots.indices) {
          quarterEnemyId += robots(i).getID.toString
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
          val robots = rc.senseNearbyRobots(SCOUT.sensorRadius, enemy)
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
        val robots = rc.senseNearbyRobots(SCOUT.sensorRadius, enemy)
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
          val robots = rc.senseNearbyRobots(SCOUT.sensorRadius, enemy)
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
        val robots = rc.senseNearbyRobots(SCOUT.sensorRadius, enemy)
        for(i <- robots.indices) {
          quarterEnemyId += robots(i).getID.toString
        }
      } catch {
        case e: GameActionException =>
          System.out.println("ShadowScout Exception")
          e.printStackTrace()
      }
      rc.move(quarterBottomLeft);//Find enemy robots
      val robots = rc.senseNearbyRobots(SCOUT.sensorRadius, enemy)
      for(i <- robots.indices) {
        quarterEnemyId += robots(i).getID.toString
      }
    }
    quarterEnemyId.size
  }
}
