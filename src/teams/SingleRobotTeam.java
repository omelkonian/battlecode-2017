package teams;

import battlecode.common.GameActionException;
import battlecode.common.RobotType;

import static battlecode.common.RobotType.GARDENER;
import static utils.Building.waitBuild;
import static utils.Current.*;
import static utils.Config.Channel.*;
import static utils.Movement.randomDirection;

/**
 * Base class for single robot scala.teams (for testing).
 */
public abstract class SingleRobotTeam implements RobotTeam {

    private RobotType robotType;

    SingleRobotTeam(RobotType robotType) {
        this.robotType = robotType;
    }

    protected abstract void runSingle();

    @Override
    public void run() throws GameActionException {
        RobotType myType = MyType();
        switch (robotType) {
            case ARCHON:
                runSingle();
                break;
            case GARDENER:
                switch (myType) {
                    case ARCHON:
                        archonSpawnSingle();
                        break;
                    case GARDENER:
                        runSingle();
                }
            default:
                switch (myType) {
                    case ARCHON:
                        archonSpawnSingle();
                        break;
                    case GARDENER:
                        //waitBuild(robotType)
                        break;
                    default:
                        if (myType == robotType)
                            runSingle();
                }
        }
    }

    private void archonSpawnSingle() {
        int channel = ARCHON_SPAWNED.index();
        try {
            if (I.readBroadcast(channel) == 0) {
                // Set flag to kill next archons
                I.broadcast(channel, 1);
                // Construct a gardener and die
                waitBuild(GARDENER, randomDirection(), d -> randomDirection());
            }
        } catch (GameActionException e) {
            System.out.println("Invalid channel: " + channel);
        }
    }

}
