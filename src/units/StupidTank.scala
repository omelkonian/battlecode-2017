package units

import java.util
import java.util.{Collections, Random}

import battlecode.common._
import utils.Dir
import utils.Movement.tryMove
import utils.Current.I

/**
  * Default tank unit.
  */
class StupidTank extends RobotUnit {
  def getOpponentDir(rc : RobotController): Direction = {
    return Direction.getEast
  }

  @throws(classOf[GameActionException])
  override def runStep(): Unit = {
    val rc = I
    val oppTeam = rc.getTeam.opponent()
    val opponents = rc.senseNearbyRobots(-1, oppTeam)
    var dir : Direction = null
    if (!opponents.isEmpty) {
      System.out.println("Found " + opponents.length + " opponents")
      val loc = rc.getLocation
      val dirs : util.LinkedList[Float] = new util.LinkedList[Float]
      var i = 0
      for (i <- 0 to opponents.length - 1) {
        dirs.add(loc.directionTo(opponents(i).getLocation).radians)
      }
      var mostTogether = 0
      var targetRad = 0f
      var curTogether = 0
      var curRadStart = 999f
      var curRadEnd = 999f
      val dirIt = dirs.iterator
      while (dirIt.hasNext) {
        val d = dirIt.next
        if ((d - curRadEnd)*(d-curRadEnd) < 0.7f) {
          curTogether += 1
          curRadEnd = d
          if (curTogether > mostTogether) {
            targetRad = curRadStart// Dir.avgDir(Array(curRadStart, curRadEnd))
            mostTogether = curTogether
          }
        } else {
          curRadStart = d
          curRadEnd = d
          curTogether = 1
          if (mostTogether == 0) {
            targetRad = curRadStart// Dir.avgDir(Array(curRadStart, curRadEnd))
            mostTogether = curTogether
          }
        }
      }
      val targetDir = new Direction(targetRad).rotateLeftRads(new Random().nextFloat()/2f - 0.25f)
      if (mostTogether == 1)
        rc.fireSingleShot(targetDir)
      else if (mostTogether == 2)
        rc.fireTriadShot(targetDir)
      else
        rc.firePentadShot(targetDir)
      dir = targetDir.rotateLeftRads(0.5f)
    } else {
      val trees = rc.senseNearbyTrees(-1, oppTeam)
      if (!trees.isEmpty) {
        val treeLoc = trees(0).getLocation
        dir = rc.getLocation.directionTo(treeLoc)
      } else {
        dir = getOpponentDir(rc).rotateLeftRads(new Random().nextFloat()/2f - 0.25f)
      }
    }
    if (!tryMove(dir)) {
      dir.rotateRightDegrees(10.0f)
    }
    Clock.`yield`
  }
}
