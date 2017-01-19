package units;

import battlecode.common.GameActionException;

import static utils.Winning.tryInstantWin;

/**
 * Base trait for units.
 */
public interface RobotUnit {

    default void run() {
        try {
            while (true) {
                tryInstantWin();
                runStep();
            }
        } catch (GameActionException e) {
            System.out.println("Game Exception");
            e.printStackTrace();
        }
    }

    void runStep() throws GameActionException;
}
