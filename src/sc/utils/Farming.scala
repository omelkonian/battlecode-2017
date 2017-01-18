package sc.utils

import battlecode.common.{Direction, MapLocation}
import j.utils.Current.{Here, I}
import Execution._
import Movement._

/**
  * Farming utilities.
  */
object Farming {

  /**
    * Skips turns until it can water a tree and then water it.
    * @param loc the location to water
    * @param plantIfMissing plants tree if it is not there
    */
  def waitWater(loc: MapLocation, plantIfMissing: Boolean = false): Unit = {
    if (plantIfMissing && !I.isLocationOccupiedByTree(loc))
      waitPlant(Here directionTo loc)
    waitAndThen[MapLocation](
      condition = I canWater _,
      action = I water _,
      variance = d => d,
      init = loc
    )
  }

  /**
    * Skips turns until it can plant a tree and then plants it.
    * @param dir the direction to plant the tree
    * @param offset the offset to move, plant and move back
    */
  def waitPlant(dir: Direction, offset: Float = 0): Unit = {
    waitMove(dir, offset)
    waitAndThen[Direction](
      condition = I canPlantTree _,
      action = I plantTree _,
      variance = d => d,
      init = dir
    )
    waitMove(dir.opposite(), offset)
  }


  // Implicit class
  implicit class FarmingRobot(erc: EnrichedRC) {
    def waitWater: (MapLocation, Boolean) => Unit = Farming.waitWater
    def waitPlant: (Direction, Float) => Unit = Farming.waitPlant
  }
}
