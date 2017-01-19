package units;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;

import java.util.Arrays;
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
import static utils.Winning.tryInstantWin;


/**
 * Gardener that creates and maintains farms.
 *
 * Invariant: Will never build robots
 * Invariant: When it has created its farm, it will stay inside it forever and just maintain it
 */
public class Farmer implements RobotUnit {

    private static final int FARM_RADIUS = 4;

    @Override
    public void runStep() throws GameActionException {
        // Find farm location
        System.out.println("Finding farm...");
        findFarm();
        waitMove(N);

        // Create farm
        System.out.println("Creating farm...");
        circularly(dir -> waitPlant(dir, 0));

        // Maintain farm
        System.out.println("Maintaining farm...");
        endlessly(
            () -> {
                tryInstantWin();
                circularly(
                    dir -> {
                        try {
                            waitWater(Here().add(dir, 2), true);
                        } catch (GameActionException e) {
                            e.printStackTrace();
                        }
                    }
                );
            }
        );

    }

    private void findFarm() {
        wanderUntil(
            Farmer::isLocationSuitableForFarming,
            Movement::randomDirection
        );
    }

    private static Boolean isLocationSuitableForFarming() {
        MapLocation here = Here();
        try {
            return I.onTheMap(here, FARM_RADIUS) && !I.isCircleOccupiedExceptByThisRobot(here, FARM_RADIUS);
        } catch (GameActionException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void circularly(Consumer<Direction> action) {
        List<Pair<Direction, Direction>> directions = Arrays.asList(
                new Pair<>(N, E),
                new Pair<>(NE, S),
                new Pair<>(E, S),
                new Pair<>(SE, W),
                new Pair<>(S, W),
                new Pair<>(SW, N),
                new Pair<>(W, N),
                new Pair<>(NW, E)
        );
        for (Pair<Direction, Direction> entry : directions) {
            action.accept(entry.fst);
            waitMove(entry.snd);
        }
    }
}
