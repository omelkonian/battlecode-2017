package sc.utils

import battlecode.common.GameActionException
import j.utils.Constants.BULLETS_TO_WIN
import j.utils.Current.I

/**
  * Winning utilities.
  */
object Winning {

  /**
    * If all required bullets are there, donates them all and wins the game.
    */
  def tryInstantWin(): Unit =
    try {
      if (I.getTeamBullets >= BULLETS_TO_WIN)
        I donate BULLETS_TO_WIN
    }
    catch { case _: GameActionException => }

  // Implicit class
  implicit class WinningRobot(erc: EnrichedRC) {
    def tryInstantWin: () => Unit = Winning.tryInstantWin
  }

}
