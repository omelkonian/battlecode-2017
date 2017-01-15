package example;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import static utils.Current.setRC;
import static utils.Config.Player1;

public strictfp class RobotPlayer {

    @SuppressWarnings("unused")
    public static void run(RobotController rc) throws GameActionException {
        setRC(rc);
        Player1.run();
    }
}