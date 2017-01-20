package utils;
import battlecode.common.Direction;
import static battlecode.common.GameConstants.*;
import static utils.Current.*;

/**
 * Useful extra constants.
 */
public class Constants {
    // Directions
    public static Direction N = Direction.getNorth();
    public static Direction E = Direction.getEast();
    public static Direction S = Direction.getSouth();
    public static Direction W = Direction.getWest();
    public static Direction NE = N.rotateRightDegrees(45);
    public static Direction NW = N.rotateLeftDegrees(45);
    public static Direction SE = S.rotateLeftDegrees(45);
    public static Direction SW = S.rotateRightDegrees(45);

    // Victory
    public static float currentExchangeRate() {
        return VP_BASE_COST + (I.getRoundNum() * VP_INCREASE_PER_ROUND);
    }
    public static int victoryPointsLeftToWin() {
        return VICTORY_POINTS_TO_WIN - I.getTeamVictoryPoints();
    }
    public static float bulletsLeftToWin() {
        return victoryPointsLeftToWin() * currentExchangeRate();
    }

    // PI
    public static float PI = (float) Math.PI;
}
