package geometry;

import battlecode.common.MapLocation;

public final class GeometricUtilities {

    /**
     * @param p1 the first point
     * @param p2 the second point
     * @return the midpoint between them
     */
    public static MapLocation midpoint(MapLocation p1, MapLocation p2) {
        return new MapLocation((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
    }

}
