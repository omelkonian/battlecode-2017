package units
import battlecode.common._
import utils.Current.I
import utils.Sensing._

/**
  * Gardener to test pathfinding.
  */
class Pathfinder extends RobotUnit {

  @throws(classOf[GameActionException])
  override def runStep(): Unit = {
    System.out.println(I senseNearbyObstacles())
  }


}
