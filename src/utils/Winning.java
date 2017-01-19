package utils;

import battlecode.common.GameActionException;

import static utils.Constants.BULLETS_TO_WIN;
import static utils.Current.*;

/**
 * Winning utilities.
 */
public class Winning {

    public static void tryInstantWin() {
        if (I.getTeamBullets() >= BULLETS_TO_WIN)
            try {
                I.donate(BULLETS_TO_WIN);
            } catch (GameActionException e) {
                System.out.println("Cannot donate");
                e.printStackTrace();
            }
    }
}
