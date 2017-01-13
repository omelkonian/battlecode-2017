package units
import battlecode.common.GameActionException
import utils.Constants.BULLETS_TO_WIN
import utils.Current.I

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
        runTurn()
      } catch {
        case e: Exception =>
          System.out.println("GameException")
          e.printStackTrace()
          return
      }
  }

  @throws(classOf[GameActionException])
  def runTurn(): Unit
}
