package sc.units

import battlecode.common.{Direction, GameActionException}
import j.utils.Current._
import sc.utils.Execution._
import sc.utils.Farming._
import sc.utils.Movement._
import sc.utils.Winning._
import j.utils.Constants._

/**
  * Gardener that creates and maintains farms.
  *
  * Invariant: Will never build robots
  * Invariant: When it has created its farm, it will stay inside it forever and just maintain it
  */
class Farmer extends RobotUnit {

  val FARM_RADIUS = 4

  @throws(classOf[GameActionException])
  override def runStep(): Unit = {
    // Find farm location
    System.out.println("Finding farm location...")
    findFarmLocation()
    System.out.println(s"Found farm location at $Here")

    waitMove(N)

    // Create farm
    System.out.println("Creating farm...")
    circularly {
      waitPlant(_)
    }

    // Maintain farm
    System.out.println("Maintaining farm...")
    endlessly {
      () => {
        tryInstantWin()
        circularly {
          dir => waitWater(Here add(dir, 2), plantIfMissing = true)
        }
      }
    }
  }

  def findFarmLocation(): Unit =
    // Wander until you find a suitable farm location
    wanderUntil(
      condition = () => I.onTheMap(Here, FARM_RADIUS) && !I.isCircleOccupiedExceptByThisRobot(Here, FARM_RADIUS)
    )

  def circularly(action: Direction => Unit): Unit =
    for ((actionDir, moveDir) <- List(
      (N, E), (NE, S), (E, S), (SE, W),
      (S, W), (SW, N), (W, N), (NW, E)
    )) {
      action(actionDir)
      waitMove(moveDir)
    }
}
