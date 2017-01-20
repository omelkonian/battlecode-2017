package geometry;

import battlecode.common.MapLocation;

/**
 * A circle on the plane.
 */
public class Circle implements Shape {
    MapLocation center;
    float radius;

    public Circle(MapLocation center, float radius) {
        this.center = center;
        this.radius = radius;
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
       return new Circle(center.translate(dx, dy), radius);
    }
}
