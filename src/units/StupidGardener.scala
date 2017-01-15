package units

import battlecode.common.RobotType._
import battlecode.common._
import utils.Dir
import utils.Movement.{randomDirection, tryMove}
import utils.Current.I

/**
  * Default gardener unit.
  */
class StupidGardener extends RobotUnit {


  @throws(classOf[GameActionException])
  override def runStep(): Unit = {
    try {
      val rc = I
      var moveDir = randomDirection()
      1.to(10).foreach(_ => {
        if (StupidGardener.treesBuilt < 6 &&
          rc.getTeamBullets >= GameConstants.BULLET_TREE_COST)
          for (dir <- Dir.dirs) {
            if (rc.canPlantTree(dir)) {
              rc.plantTree(dir)
              StupidGardener.treesBuilt += 1
            }
          }

        Dir.dirs.foreach(dir => {
          if (rc.canBuildRobot(TANK, dir) && Math.random() < .01) {
            rc.buildRobot(TANK, dir)
          }
        })

        rc.senseNearbyTrees(-1.0f, rc.getTeam).foreach(tree => {
          if (tree.getHealth / tree.getMaxHealth < 0.8) {
            if (rc.canWater(tree.getID))
              rc.water(tree.getID)
            else
              moveDir = rc.getLocation.directionTo(tree.getLocation)
          }
        })

        // Move randomly
        if (!tryMove(moveDir))
          moveDir = randomDirection()

        // Clock.yield() makes the robot wait until the next turn, then it will perform this loop again
        Clock.`yield`()
      })

    } catch {
      case e: Exception =>
        System.out.println("Gardener Exception")
        e.printStackTrace()
    }
  }
}

object StupidGardener {
  private var treesBuilt: Int = 0
}
