package sc.utils

import battlecode.common.Clock

/**
  * Collision utilities.
  */
object Execution {

  /**
    * Endless loop.
    * @param action the action to repeat
    */
  def endlessly(action: () => Unit): Unit =
    while (true) action()

  /**
    * Skips turns until given condition is true and then executes some action.
    */
  def waitAndThen[T](init: T,
                     condition: T => Boolean,
                     action: T => Unit,
                     variance: T => T = identity _): Unit = {
    var current = init
    while (!condition(current)) {
      current = variance(current)
      Clock.`yield`()
    }
    action(current)
  }
}
