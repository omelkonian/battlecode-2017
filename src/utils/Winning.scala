package utils

import battlecode.common.GameActionException
import utils.Constants.BULLETS_TO_WIN
import utils.Current.I

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

}
