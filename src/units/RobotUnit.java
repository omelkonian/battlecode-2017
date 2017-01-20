package units;

import battlecode.common.GameActionException;

/**
 * Base trait for units.
 */
public interface RobotUnit {

    /*
     Fine-grained stages
      */
    default void prerun() {}
    default void postrun() {}

    /*
     General
      */
    default void run() {
        while (true)
            try {
                prerun();
                runStep();
                postrun();
            }
            catch (GameActionException e) {
                System.out.println("Game Exception");
                e.printStackTrace();
            }
    }

    void runStep() throws GameActionException;
}
