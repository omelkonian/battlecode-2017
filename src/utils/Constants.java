package utils;
import battlecode.common.Direction;
import battlecode.common.GameConstants;

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
    public static float BULLETS_TO_WIN =
            GameConstants.VICTORY_POINTS_TO_WIN * GameConstants.BULLET_EXCHANGE_RATE;

    // PI
    public static float PI = (float) Math.PI;
    public static float PI_2 = PI / 2;
}
