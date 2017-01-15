package utils
import battlecode.common.BodyInfo
import utils.Current.I

/**
  * Sensing utilities.
  */
object Sensing {

  /**
    * Senses all nearby obstacles (i.e. trees and robots).
    * @return The obstacles' info
    */
  def senseNearbyObstacles(): Array[BodyInfo] =
    I.senseNearbyTrees() ++ I.senseNearbyRobots()

}
