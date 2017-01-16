package utils

import battlecode.common.{Direction, MapLocation}
import utils.Execution._
import utils.Movement.waitMove
import utils.Current.{I, Here}

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
    waitAndThen(
      () => I canWater loc,
      () => I water loc
    )
/*    waitAndThen[MapLocation](
      condition = I canWater _,
      action = I water _,
      variance = d => d,
      init = loc
    )*/
  }

  /**
    * Skips turns until it can plant a tree and then plants it.
    * @param dir the direction to plant the tree
    * @param offset the offset to move, plant and move back
    */
  def waitPlant(dir: Direction, offset: Float = 0): Unit = {
    waitMove(dir, offset)
    waitAndThen2[Direction](
      condition = I canPlantTree _,
      action = I plantTree _,
      variance = d => d,
      init = dir
    )
    waitMove(dir.opposite(), offset)
  }
}
