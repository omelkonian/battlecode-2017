package utils;

import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.RobotType;
import battlecode.common.Team;

/**
 * Utilities concerning current robot.
 */
public class Current {
    // Current robot controller
    public static RobotController I;

    public static MapLocation Here() {
        return I.getLocation();
    }

    public static boolean AmI(RobotType robotType) {
        return I.getType() == robotType;
    }

    public static RobotType MyType() {
        return I.getType();
    }

    public static Team MyTeam() {
        return I.getTeam();
    }

}
