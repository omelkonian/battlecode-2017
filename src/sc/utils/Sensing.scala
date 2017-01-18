package sc.utils

import battlecode.common.BodyInfo
import j.utils.Current.I

/**
  * Sensing utilities.
  */
object Sensing {

  type Filter[T] = T => Boolean

  /**
    * Senses all nearby obstacles (i.e. trees and robots).
    * @return The obstacles' info
    */
  def senseNearbyObstacles(): Seq[BodyInfo] =
    I.senseNearbyTrees() ++ I.senseNearbyRobots()

  /**
    * Filters data depending on several filters.
    * @param source initial data
    * @param filters predicates that should hold
    * @param notFilters predicates that should not hold
    * @tparam T the type of data
    */
  def filterSource[T](source: Seq[T],
                      filters: Seq[Filter[T]] = Seq(),
                      notFilters: Seq[Filter[T]] = Seq()): Seq[T] =
    source.filter { elem =>
      filters.forall(_(elem)) && !notFilters.exists(_(elem))
    }
//   .filterNot { elem => notFilters.exists(_(elem)) }


  // Implicit class
  implicit class SensingRobot(erc: EnrichedRC) {
    def senseNearbyObstacles: () => Seq[BodyInfo] = Sensing.senseNearbyObstacles
    def filterSource[T]: (Seq[T], Seq[Filter[T]], Seq[Filter[T]]) => Seq[T] =
      Sensing.filterSource[T]
  }
}
