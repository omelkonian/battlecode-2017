package utils;

import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.RobotType;

/**
 * Utilities concerning current robot.
 */
public class Current {
    // Current robot controller
    public static void setRC(RobotController rc) {
        I = rc;
    }
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
}
