package utils;

import battlecode.common.Clock;

import java.util.function.*;
import java.util.stream.IntStream;

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
        while (!condition.getAsBoolean()) {
            System.out.println(".");
            Clock.yield();
        }
    }

    /**
     * Skips turns until given condition is true and then executes some action.
     */
    public static <T> void waitAndThen(T init, Predicate<T> condition, Consumer<T> action, Function<T, T> variance) {
        T current = init;
        while (!condition.test(current)) {
            System.out.println("..");
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
}
