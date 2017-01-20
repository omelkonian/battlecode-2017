package geometry;

import battlecode.common.MapLocation;

/**
 * A line on the plane.
 */
public class Line implements Shape {
    MapLocation p1, p2;

    public Line(MapLocation p1, MapLocation p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public Position positionOf(MapLocation other) {
        throw new RuntimeException("Not implemented.");
    }

    @Override
    public boolean isInside(Shape other) {
        throw new RuntimeException("Not Implemented.");
    }

    @Override
    public boolean interesectsWith(Shape other) {
        throw new RuntimeException("Not Implemented.");
    }

    @Override
    public Shape translate(float dx, float dy) {
       return new Line(p1.translate(dx, dy), p2.translate(dx, dy));
    }
}
