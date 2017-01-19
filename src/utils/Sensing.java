package utils;

import battlecode.common.BodyInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static utils.Current.I;

public final class Sensing {

    public static List<BodyInfo> senseNearbyObstacles() {
        BodyInfo[] robots = I.senseNearbyRobots();
        BodyInfo[] trees = I.senseNearbyTrees();
        List<BodyInfo> obstacles = new ArrayList<>(robots.length + trees.length);
        Collections.addAll(obstacles, robots);
        Collections.addAll(obstacles, trees);
        return obstacles;
    }

    public static <T> List<T> filterSource(List<T> source, List<Predicate<T>> filters) {
        return source.stream().filter(t -> filters.stream().allMatch(f -> f.test(t))).collect(Collectors.toList());
    }
}
