package units

import java.util
import java.util.Random

import battlecode.common._
import utils.Current.I
import utils.Dir
import utils.Execution.endlessly
import utils.Movement.{curDirTo, tryMove}

class NeutralTreeFarmer extends RobotUnit {
  @throws(classOf[GameActionException])
  override def runStep(): Unit = {
    var chosenDir : Direction = Dir.dirs(new Random().nextInt(Dir.dirs.length))
    val ignoredTrees: util.HashSet[Int] = new util.HashSet()

    endlessly(() => {
      val opponents = I.senseNearbyRobots(-1, I.getTeam.opponent())
      if (!opponents.isEmpty) {
        chosenDir = opponents(0).getLocation.directionTo(I.getLocation)
        while (!I.senseNearbyRobots(-1, I.getTeam.opponent()).isEmpty) {
          tryMove(chosenDir)
          Clock.`yield`
        }
      }
      if (I.canBuildRobot(battlecode.common.RobotType.SCOUT, chosenDir.opposite))
        I.buildRobot(battlecode.common.RobotType.SCOUT, chosenDir.opposite)
      val neutralTrees = I.senseNearbyTrees(-1, battlecode.common.Team.NEUTRAL)
        .toList
        .sortBy((t) => t.getLocation.distanceTo(I.getLocation))
        .filterNot(t => t.containedBullets == 0 || ignoredTrees.contains(t.getID))
      neutralTrees.foreach(t => {
        while (tryMove(curDirTo(t.getLocation))) {
          Clock.`yield`
        }
        if (I.canShake(t.getID))
          I.shake(t.getID)
        else
          ignoredTrees.add(t.getID)
      })
      var offs = 0.1f
      while (!tryMove(chosenDir)) {
        if (!I.onTheMap(I.getLocation.add(chosenDir, 3.0f)))
          System.out.println("Found edge of map: " + I.getLocation)
        chosenDir = chosenDir.rotateRightRads(offs)
        offs *= -1.5f
      }
      Clock.`yield`
    })
  }
}
