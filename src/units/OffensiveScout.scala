package units

import battlecode.common.{Clock, GameActionException, RobotType}
import utils.Current.I
import utils.Execution.endlessly
import java.util.Random

import utils.Movement.{curDirTo, randomDirection, tryMove}

class OffensiveScout extends RobotUnit {
  @throws(classOf[GameActionException])
  override def runStep(): Unit = {
    var dir = randomDirection()

    endlessly(() => {
      var off = 0.1f
      val rand = new Random
      val neutralTrees = I.senseNearbyTrees(-1, battlecode.common.Team.NEUTRAL)
        .toList
        .sortBy((t) => t.getLocation.distanceTo(I.getLocation))
        .filterNot(t => t.containedBullets == 0)
      neutralTrees.foreach(t => {
        while (tryMove(curDirTo(t.getLocation)) && !I.canShake(t.getID)) {
          Clock.`yield`
        }
        if (I.canShake(t.getID)) {
          I.shake(t.getID)
          Clock.`yield`
        }
      })
      val opponents = I.senseNearbyRobots(-1, I.getTeam.opponent()).toList.sortBy(r => {
        r.getType match {
          case RobotType.ARCHON => 1
          case RobotType.GARDENER => 2
          case RobotType.LUMBERJACK => 4
          case RobotType.SOLDIER => 5
          case RobotType.TANK => 6
          case RobotType.SCOUT => 3
        }
      })
      opponents.foreach(opp => {
        var opp2 = opp
        while (I.canSenseRobot(opp2.getID)) {
          while (opp2.getLocation.distanceTo(I.getLocation) > 7.0f) {
            opp2 = I.senseRobot(opp2.getID)
            while (!tryMove(curDirTo(opp2.getLocation).rotateRightRads(rand.nextFloat() % 2.0f - 1.0f))) {}
            if (I.canFireSingleShot)
              I.fireSingleShot(curDirTo(opp2.getLocation))
            Clock.`yield`
          }
          while (opp2.getLocation.distanceTo(I.getLocation) < 3.0f) {
            opp2 = I.senseRobot(opp2.getID)
            while (!tryMove(curDirTo(opp2.getLocation).opposite.rotateRightRads(rand.nextFloat() % 2.0f - 1.0f))) {}
            if (I.canFireSingleShot)
              I.fireSingleShot(curDirTo(opp2.getLocation))
            Clock.`yield`
          }
          opp2 = I.senseRobot(opp2.getID)
          tryMove(curDirTo(opp2.getLocation).rotateRightDegrees(75))
          if (I.canFireSingleShot)
            I.fireSingleShot(curDirTo(opp2.getLocation))
          Clock.`yield`
        }
      })
      while (!tryMove(dir)) {
        dir = dir.rotateRightRads(off)
        off *= -1.5f
      }
      Clock.`yield`
    })
  }
}
