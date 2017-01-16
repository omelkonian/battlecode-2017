package units
import battlecode.common._
import utils.Current.{I, Here}
import utils.Movement.{tryMove, waitMove, wanderUntil}
import utils.Execution.endlessly
import utils.Winning.tryInstantWin
import utils.Farming.{waitPlant, waitWater}
//import utils.Pathfinding.moveAwayFromObstacles
import utils.Constants._

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
    while (
      !I.onTheMap(Here, FARM_RADIUS) || I.isCircleOccupiedExceptByThisRobot(Here, FARM_RADIUS)
    )
      System.out.println()
//    tryMove(moveAwayFromObstacles())
    /*wanderUntil(
      condition = () =>
        !I.onTheMap(Here, FARM_RADIUS) || I.isCircleOccupiedExceptByThisRobot(Here, FARM_RADIUS),
      nextDirection = () => {
        moveAwayFromObstacles()
      }
    )*/

  def circularly(action: Direction => Unit): Unit =
    for ((actionDir, moveDir) <- List(
      (N, E), (NE, S), (E, S), (SE, W),
      (S, W), (SW, N), (W, N), (NW, E)
    )) {
      action(actionDir)
      waitMove(moveDir)
    }

  def sense[T](source: List[BodyInfo]): List[Int] = source.map(_ => 1)
}
