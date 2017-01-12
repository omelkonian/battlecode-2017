package example;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import teams.DefaultTeam;
import teams.RobotTeam;

public strictfp class RobotPlayer {
    private static RobotTeam defaultTeam = new DefaultTeam();
    public static RobotController rc;

    /**
     * run() is the method that is called when a robot is instantiated in the Battlecode world.
     * If this method returns, the robot dies!
     **/
    @SuppressWarnings("unused")
    public static void run(RobotController rc) throws GameActionException {
        RobotPlayer.rc = rc;
        defaultTeam.run(rc);
    }
}