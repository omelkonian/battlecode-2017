package utils;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotType;
import static battlecode.common.RobotType.*;

import java.util.function.Function;

import static utils.Current.*;
import static utils.Execution.*;

/**
 * Building utilities.
 */
public final class Building {

    /**
     * Skips turns until it can build a robot and then builds it.
     * @param robotType the robot to build
     * @param direction the direction in which to build
     * @param variance alter direction on each try
     */
    public static void waitBuild(RobotType robotType, Direction direction, Function<Direction, Direction> variance) {
        switch (robotType) {
            case ARCHON:
                throw new RuntimeException("Cannot build Archon");
            case GARDENER:
                assert AmI(ARCHON);
                break;
            default:
                assert AmI(GARDENER);
        }

        waitAndThen(
            direction,
            dir -> I.canBuildRobot(robotType, dir),
            dir -> {
                try {
                    I.buildRobot(robotType, dir);
                } catch (GameActionException e) {
                    e.printStackTrace();
                }
            },
            variance
        );
    }
}
