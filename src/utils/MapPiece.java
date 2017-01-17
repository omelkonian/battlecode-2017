package utils;

import battlecode.common.MapLocation;
import battlecode.common.RobotInfo;

import java.util.ArrayList;

/**
 * Created by gdimopou on 17/01/2017.
 */
public class MapPiece {
    private MapLocation bottomRight;
    private MapLocation bottomLeft;
    private MapLocation topRight;
    private MapLocation topLeft;
    private ArrayList<RobotInfo> robots;


    public MapPiece(MapLocation bottomRight, MapLocation bottomLeft, MapLocation topRight, MapLocation topLeft) {
        robots = new ArrayList<RobotInfo>();
        this.bottomRight = bottomRight;
        this.bottomLeft = bottomLeft;
        this.topRight = topRight;
        this.topLeft = topLeft;
    }

    public MapLocation getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(MapLocation bottomRight) {
        this.bottomRight = bottomRight;
    }

    public MapLocation getBottomLeft() {
        return bottomLeft;
    }

    public void setBottomLeft(MapLocation bottomLeft) {
        this.bottomLeft = bottomLeft;
    }

    public MapLocation getTopRight() {
        return topRight;
    }

    public void setTopRight(MapLocation topRight) {
        this.topRight = topRight;
    }

    public MapLocation getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(MapLocation topLeft) {
        this.topLeft = topLeft;
    }

    public boolean isOnMapPiece(MapLocation mapLocation) {
        if((mapLocation.x < bottomLeft.x) || (mapLocation.x > bottomRight.x) ||
            (mapLocation.y < bottomLeft.y) || (mapLocation.y > topLeft.y)) {
            return false;
        }
        return true;
    }

    public ArrayList<RobotInfo> getRobots() {
        return robots;
    }

    public void setRobots(ArrayList<RobotInfo> robots) {
        this.robots = robots;
    }

    @Override
    public String toString() {
        return "MapPiece{" +
                "bottomRight=" + bottomRight +
                ", bottomLeft=" + bottomLeft +
                ", topRight=" + topRight +
                ", topLeft=" + topLeft +
                ", robots=" + robots +
                '}';
    }
}
