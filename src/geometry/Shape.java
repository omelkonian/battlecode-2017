package geometry;

import battlecode.common.MapLocation;

/**
 * Interface for geometric shapes.
 */
interface Shape {

    // Positioning
    Position positionOf(MapLocation other);
    boolean isInside(Shape other);

    // Intersection
    boolean interesectsWith(Shape other);

    // Transformations
    Shape translate(float dx, float dy);

}
