package sc.units

import battlecode.common.GameActionException
import j.utils.Constants.BULLETS_TO_WIN
import j.utils.Current.I

/**
  * Base trait for units.
  */
trait RobotUnit {

  @throws(classOf[GameActionException])
  def run(): Unit = {
    while (true)
      try {
        if (I.getTeamBullets >= BULLETS_TO_WIN)
          I donate BULLETS_TO_WIN
        runStep()
      } catch {
        case e: Exception =>
          System.out.println("GameException")
          e.printStackTrace()
          return
      }
  }

  @throws(classOf[GameActionException])
  def runStep(): Unit
}
