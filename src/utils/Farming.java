package utils;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import static utils.Current.*;
import static utils.Movement.*;
import static utils.Execution.*;

/**
 * Farming utilities.
 */
public final class Farming {

    public static void waitWater(MapLocation loc, boolean plantIfMissing) throws GameActionException {
        if (plantIfMissing && !I.isLocationOccupiedByTree(loc))
            waitPlant(Here().directionTo(loc), 0);
        waitAndThen(
            () -> I.canWater(loc),
            () -> {
                try {
                    I.water(loc);
                } catch (GameActionException e) {
                    e.printStackTrace();
                }
            }
        );
    }

    /**
     * Skips turns until it can plant a tree and then plants it.
     * @param dir the direction to plant the tree
     * @param offset the offset to move, plant and move back
     */
    public static void waitPlant(Direction dir, float offset) {
        waitMove(dir, offset);
        waitAndThen(
            () -> I.canPlantTree(dir),
            () -> {
                try {
                    I.plantTree(dir);
                } catch (GameActionException e) {
                    e.printStackTrace();
                }
            }
        );
        waitMove(dir.opposite(), offset);
    }
}
