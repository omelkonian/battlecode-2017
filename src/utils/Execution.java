package utils;

import battlecode.common.Clock;
import battlecode.common.Direction;
import battlecode.common.GameActionException;

import java.util.List;
import java.util.function.*;

import static utils.Movement.waitMove;

/**
 * Collision utilities.
 */
public final class Execution {

    /**
     * Endless loop.
     * @param action the action to repeat
     */
    public static void endlessly(Runnable action) {
        while (true)
            action.run();
    }

    /**
     * Repeat an action N times.
     * @param N
     * @param action the action to repeat
     */
    public static void repeat(int N, Runnable action) {
        for (int i = 0; i < N; i++)
            action.run();
    }

    /**
     * Skip turns until given condition is true.
     * @param condition the condition to satisfy
     */
    public static void await(BooleanSupplier condition) {
        while (!condition.getAsBoolean())
            Clock.yield();
    }

    /**
     * Skips turns until given condition is true and then executes some action.
     */
    public static <T> void waitAndThen(T init, Predicate<T> condition, Consumer<T> action, Function<T, T> variance) {
        T current = init;
        while (!condition.test(current)) {
            current = variance.apply(current);
            Clock.yield();
        }
        action.accept(current);
    }

    /**
     * Skips turns until given condition is true and then executes some action.
     */
    public static void waitAndThen(BooleanSupplier condition, Runnable action) {
        await(condition);
        action.run();
    }


    public static void movingAction(Consumer<Direction> action,
                                    List<Pair<Direction, Direction>> directions,
                                    float stride) {
        for (Pair<Direction, Direction> entry : directions) {
            if (entry.fst != null)
                action.accept(entry.fst);
            if (entry.snd != null)
                waitMove(entry.snd, stride);
        }
    }
}
