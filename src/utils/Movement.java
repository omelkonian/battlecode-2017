package utils;

import battlecode.common.Clock;
import battlecode.common.Direction;
import battlecode.common.GameActionException;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import static utils.Current.*;
import static utils.Execution.*;

/**
 * Movement utilities.
 */
public final class Movement {

    /**
     * Wanders around with a specific strategy until a condition is met.
     * @param condition the condition to satisfy
     * @param nextDirection wandering strategy
     */
    public static void wanderUntil(BooleanSupplier condition, Supplier<Direction> nextDirection) {
        while (!condition.getAsBoolean()) {
            try {
                tryMove(nextDirection.get());
            } catch (GameActionException e) {
                e.printStackTrace();
            }
            Clock.yield();
        }
    }

    /**
     * Moves to a single direction, skipping turns if necessary.
     * @param dir the direction to move
     */
    public static void waitMove(Direction dir) {
        waitAndThen(
            () -> !I.hasMoved() && I.canMove(dir),
            () -> {
                try {
                    I.move(dir);
                } catch (GameActionException e) {
                    e.printStackTrace();
                }
            }
        );
    }

    /**
     * Moves to a single direction, skipping turns if necessary.
     * @param dir the direction to move
     * @param distance the distance to move
     */
    public static void waitMove(Direction dir, float distance) {
        assert distance >= 0;
        if (distance == 0)
            return;
        float stride = MyType().strideRadius;
        // multiple strides
        if (distance > stride) {
            int moves = (int) (distance / stride);
            int rest = (int) (distance - moves);
            repeat(moves, () -> waitMove(dir));
            if (rest > 0)
                waitMove(dir, rest);
        }
        // single (small) stride
        else if (distance < stride) {
            waitAndThen(
                () -> !I.hasMoved() && I.canMove(dir, distance),
                () -> {
                    try {
                        I.move(dir, distance);
                    } catch (GameActionException e) {
                        e.printStackTrace();
                    }
                }
            );
        }
        // single (whole) stride
        else
            waitMove(dir);
    }

    /**
     * Returns a random Direction
     * @return a random Direction
     */
    public static Direction randomDirection() {
        return new Direction((float)Math.random() * 2 * (float)Math.PI);
    }

    /**
     * Attempts to move in a given direction, while avoiding small obstacles directly in the path.
     *
     * @param dir The intended direction of movement
     * @return true if a move was performed
     * @throws GameActionException
     */
    public static boolean tryMove(Direction dir) throws GameActionException {
        return tryMove(dir,20,3);
    }

    /**
     * Attempts to move in a given direction, while avoiding small obstacles direction in the path.
     *
     * @param dir The intended direction of movement
     * @param degreeOffset Spacing between checked directions (degrees)
     * @param checksPerSide Number of extra directions checked on each side, if intended direction was unavailable
     * @return true if a move was performed
     * @throws GameActionException
     */
    public static boolean tryMove(Direction dir, float degreeOffset, int checksPerSide) throws GameActionException {

        // First, try intended direction
        if (I.canMove(dir)) {
            I.move(dir);
            return true;
        }

        // Now try a bunch of similar angles
        boolean moved = false;
        int currentCheck = 1;

        while(currentCheck<=checksPerSide) {
            // Try the offset of the left side
            if(I.canMove(dir.rotateLeftDegrees(degreeOffset*currentCheck))) {
                I.move(dir.rotateLeftDegrees(degreeOffset*currentCheck));
                return true;
            }
            // Try the offset on the right side
            if(I.canMove(dir.rotateRightDegrees(degreeOffset*currentCheck))) {
                I.move(dir.rotateRightDegrees(degreeOffset*currentCheck));
                return true;
            }
            // No move performed, try slightly further
            currentCheck++;
        }

        // A move never happened, so return false.
        return false;
    }
}
