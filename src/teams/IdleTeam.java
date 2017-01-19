package teams;

import battlecode.common.Clock;
import battlecode.common.GameActionException;

/**
  * Team that just keeps the Archons idle.
  */
public class IdleTeam implements RobotTeam {

    @Override
    public void run() throws GameActionException {
        while (true)
            Clock.yield();
    }
}
