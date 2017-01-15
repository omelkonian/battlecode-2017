package example2;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import static utils.Current.setRC;
import static utils.Config.Player2;

public strictfp class RobotPlayer {

    @SuppressWarnings("unused")
    public static void run(RobotController rc) throws GameActionException {
        setRC(rc);
        Player2.run();
    }
}