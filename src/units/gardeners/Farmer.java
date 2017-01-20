package units.gardeners;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import utils.Movement;
import utils.Pair;

import static utils.Constants.*;
import static utils.Current.Here;
import static utils.Current.I;
import static utils.Execution.*;
import static utils.Movement.*;
import static utils.Farming.*;


/**
 * Gardener that creates and maintains farms.
 *
 * Invariant: Will never build robots
 * Invariant: When it has created its farm, it will stay inside it forever and just maintain it
 */
public class Farmer implements Gardener {

    int farmRadius;

    public Farmer(int farmRadius) {
        this.farmRadius = farmRadius;
    }

    @Override
    public void runStep() throws GameActionException {
        // Find farm location
        System.out.println("Finding farm...");
        findFarm();
        repeat(farmRadius - 3, () -> waitMove(NW));

        // Maintain farm
        System.out.println("Maintaining farm...");
        endlessly(
            () -> circularly(dir -> {
                try {
                    waitWater(Here().add(dir, 2), true);
                } catch (GameActionException e) {
                    e.printStackTrace();
                }},
                farmRadius
            )
        );

    }

    private void findFarm() {
        wanderUntil(
            this::isLocationSuitableForFarming,
            Movement::randomDirection
        );
    }

    private Boolean isLocationSuitableForFarming() {
        MapLocation here = Here();
        try {
            return I.onTheMap(here, farmRadius) && !I.isCircleOccupiedExceptByThisRobot(here, farmRadius);
        } catch (GameActionException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Do action in a circular manner.
     * @param action the action to execute
     * @param radius the radius of the surrounding circle (>3 && even)
     */
    private static void circularly(Consumer<Direction> action, int radius) {
        List<Pair<Direction, Direction>> dirs = new ArrayList<>();
        int extraTreesPerSide = radius - 4;
        // NW
        dirs.add(new Pair<>(NW, E));

        // N-side
        repeat(extraTreesPerSide, () -> {
            dirs.add(new Pair<>(N, E));
            dirs.add(new Pair<>(null, E));
        });
        dirs.add(new Pair<>(N, E));

        // NE
        dirs.add(new Pair<>(NE, S));

        // E-side
        repeat(extraTreesPerSide, () -> {
            dirs.add(new Pair<>(E, S));
            dirs.add(new Pair<>(null, S));
        });
        dirs.add(new Pair<>(E, S));

        // SE
        dirs.add(new Pair<>(SE, W));

        // S-side
        repeat(extraTreesPerSide, () -> {
            dirs.add(new Pair<>(S, W));
            dirs.add(new Pair<>(null, W));
        });
        dirs.add(new Pair<>(S, W));

        // SW
        dirs.add(new Pair<>(SW, N));

        // W-side
        repeat(extraTreesPerSide, () -> {
            dirs.add(new Pair<>(W, N));
            dirs.add(new Pair<>(null, N));
        });
        dirs.add(new Pair<>(W, N));

        // Perform moving action
        movingAction(action, dirs, .5f);
    }
}
