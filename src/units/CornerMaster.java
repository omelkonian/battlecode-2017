package units;

import battlecode.common.*;
import scala.tools.cmd.gen.AnyVals;
import utils.Constants;
import utils.Current;
import utils.Dir;
import utils.MapPiece;

import java.util.ArrayList;
import java.util.HashMap;

import static battlecode.common.RobotType.SCOUT;

/**
 * Created by gdimopou on 16/01/2017.
 */
public class CornerMaster implements RobotUnit {
    private HashMap<Integer,MapLocation> eastSide = null;
    private HashMap<Integer,MapLocation> westSide = null;
    private HashMap<Integer,MapLocation> northSide = null;
    private HashMap<Integer,MapLocation> southSide = null;
    private HashMap<Integer,Direction> assignedDirections = null;
    private HashMap<Direction,Float> limits;
    private ArrayList<Direction> directionsToCheck = null;
    private HashMap<Direction, MapLocation> corners = null;
    private MapLocation archonLocation;
    private MapPiece [][] quarters;
    private HashMap<Integer, RobotInfo> robotsSeen;
    private MapPiece  townLocation = null;


    @Override
    public void run() throws GameActionException {
        quarters = new MapPiece[2][2];
        archonLocation = utils.Current.I.getLocation();
        robotsSeen = new HashMap<Integer, RobotInfo>();
        limits = new HashMap<Direction, Float>();
        eastSide = new HashMap<Integer,MapLocation>();
        westSide = new HashMap<Integer,MapLocation>();
        northSide = new HashMap<Integer,MapLocation>();
        southSide = new HashMap<Integer,MapLocation>();
        assignedDirections = new HashMap<Integer,Direction>();
        corners = new HashMap<Direction, MapLocation>() ;
        directionsToCheck = new ArrayList<Direction>();
        directionsToCheck.add(Direction.getEast());
        directionsToCheck.add(Direction.getNorth());
        directionsToCheck.add(Direction.getWest());
        directionsToCheck.add(Direction.getSouth());
        while (true)    {
            try {
                if (utils.Current.I.getTeamBullets() >= Constants.BULLETS_TO_WIN) {
                    utils.Current.I.donate(Constants.BULLETS_TO_WIN);
                }
                runStep();
            } catch(Exception e) {
                    System.out.println("GameException");
                    e.printStackTrace();
                    return;
            }

        }
    }

    @Override
    public void runStep() throws GameActionException {
        if(!assignedDirections.keySet().contains(utils.Current.I.getID())) {
            if(directionsToCheck.size()>0) {
                assignedDirections.put(utils.Current.I.getID(), directionsToCheck.get(0));
            }
        }
        checkForCorner(assignedDirections.get(utils.Current.I.getID()));
        if(limits.keySet().size()==4) {
           // writeCornersOnConsole();
            if(townLocation == null) {
                constuctCorners();
                findQuarters();
                announceTownLocation();
            }
            if(townLocation != null) {
                for(MapPiece[] quarters:quarters) {
                    for(MapPiece quarter : quarters) {
                        patrolQuarter(quarter, true);
                    }
                }
                printEnemyLog();
            }
        } else {
            if(southSide.get(utils.Current.I.getID())!=null) {
                limits.put(Direction.getSouth(), southSide.get(utils.Current.I.getID()).y);
                directionsToCheck.remove(Direction.getSouth());
                if(directionsToCheck.size()>0) {
                    assignedDirections.put(utils.Current.I.getID(), directionsToCheck.get(0));
                }
            }
            if( eastSide.get(utils.Current.I.getID())!=null) {
                limits.put(Direction.getEast(), eastSide.get(utils.Current.I.getID()).x);
                directionsToCheck.remove(Direction.getEast());
                if(directionsToCheck.size()>0) {
                    assignedDirections.put(utils.Current.I.getID(), directionsToCheck.get(0));
                }
            }

            if( northSide.get(utils.Current.I.getID())!=null) {
                limits.put(Direction.getNorth(), northSide.get(utils.Current.I.getID()).y);
                directionsToCheck.remove(Direction.getNorth());
                if(directionsToCheck.size()>0) {
                    assignedDirections.put(utils.Current.I.getID(), directionsToCheck.get(0));
                }
            }

            if( westSide.get(utils.Current.I.getID())!=null) {
                limits.put(Direction.getWest(), westSide.get(utils.Current.I.getID()).x);
                directionsToCheck.remove(Direction.getWest());
                if(directionsToCheck.size()>0) {
                    assignedDirections.put(utils.Current.I.getID(), directionsToCheck.get(0));
                }
            }

            tryMoveAndScout(assignedDirections.get(utils.Current.I.getID()),0);
        }
    }

    private void announceTownLocation() {
        for(int i=0 ; i <=1; i++) {
            for(int j =0 ; j<=1 ; j++) {
                if(quarters[i][j].isOnMapPiece(archonLocation)) {
                    logCornerMasterReport(i, j);
                    try {
                        townLocation =quarters[i][j];
                        Current.I.broadcast(0,i);
                        Current.I.broadcast(1,j);
                        break;
                    } catch (GameActionException e) {
                    }
                }
            }
        }
    }

    private void logCornerMasterReport(int i, int j) {
        System.out.println("");
        System.out.println("");
        System.out.println("-CORNER MASTER REPORT-");
        System.out.println("Town Center Quarter Position: ("+i+" ,"+j+")");
        System.out.println("");
        System.out.println("Town Center Quarter Exact Position");
        System.out.println("----------------------------------");
        System.out.println(quarters[i][j].toString());
        System.out.println("----------------------------------");
        System.out.println("");
        System.out.println("Safest place to be:"+ computeSafestPlaceOfQuarter(quarters[i][j]).toString());
        printEnemyLog();
    }


    private MapLocation computeSafestPlaceOfQuarter(MapPiece mapPiece) {
        for(MapLocation corner: corners.values()) {
            if(mapPiece.getBottomLeft().equals(corner) || mapPiece.getBottomRight().equals(corner) ||
                    mapPiece.getTopLeft().equals(corner) || mapPiece.getTopRight().equals(corner)) {
                return corner;
            }
        }
        return null;
    }

    private void writeCornersOnConsole() {
        System.out.println("Found corners of the map");
        System.out.println("------------------------");
        System.out.println("NE: ("+limits.get(Direction.getEast())+", "+limits.get(Direction.getNorth())+")");
        System.out.println("SE: ("+limits.get(Direction.getEast())+", "+limits.get(Direction.getSouth())+")");
        System.out.println("SW: ("+limits.get(Direction.getWest())+", "+limits.get(Direction.getSouth())+")");
        System.out.println("NW: ("+limits.get(Direction.getWest())+", "+limits.get(Direction.getNorth())+")");
    }

    private void findQuarters() {
        for(int i=0 ; i <=1; i++) {
            for(int j =0 ; j<=1 ; j++) {
                Float westest = limits.get(Direction.getWest());
                Float eastest = limits.get(Direction.getEast());
                Float southest = limits.get(Direction.getSouth());
                Float northest = limits.get(Direction.getNorth());

                Float xStep = (eastest-westest)/2;
                Float yStep = (northest-southest)/2;

                MapLocation bottomLeft = new MapLocation(westest+i*xStep,southest+j*yStep);
                MapLocation bottomRight = new MapLocation(westest+xStep+i*xStep,southest+j*yStep);
                MapLocation topLeft = new MapLocation(westest+i*xStep,southest + yStep + j*yStep);
                MapLocation topRight = new MapLocation(westest+xStep+i*xStep,southest + yStep + j*yStep);

                quarters[i][j] = new MapPiece(bottomRight,bottomLeft, topRight, topLeft);
                organiseEnemiesInQuarters(quarters[i][j]);
            }
        }
    }

    private void organiseEnemiesInQuarters(MapPiece mapPiece) {
        for(RobotInfo robotInfo: robotsSeen.values()) {
            if(mapPiece.isOnMapPiece(robotInfo.location)) {
                mapPiece.getRobots().add(robotInfo);
            }
        }
        for(RobotInfo robotInfo: mapPiece.getRobots()) {
            robotsSeen.remove(robotInfo.getID());
        }
    }


    private Integer tryMoveAndScout(Direction direction,Integer tries) {
        if(tries>=5) return -1;
        if(utils.Current.I.canMove(direction)) {

            try {
                scoutEnemiesAround();
                utils.Current.I.move(direction);
            } catch (GameActionException e) {
            }
        } else {
            System.out.print("I can not move"+ tries);
            direction = new Direction((float) (direction.radians + Math.pow(-1,tries%2)*(Math.PI/4)*(tries+1)));
            tryMoveAndScout(direction, ++tries);
        }
        return 0;
    }

    private Integer tryMoveAndScout(MapLocation mapLocation,Integer tries) {
        if(tries>=2) return  tryMoveAndScout(new Direction(Current.I.getLocation(),mapLocation),0);
        if(utils.Current.I.canMove(mapLocation) ) {

            try {
                scoutEnemiesAround();
                utils.Current.I.move(mapLocation);
            } catch (GameActionException e) {
            }
        } else {
            System.out.print("I can not move"+ tries);
            mapLocation = new MapLocation(mapLocation.x+tries*10,mapLocation.y-tries*10);
            tryMoveAndScout(mapLocation, ++tries);
        }
        return 0;
    }

    private void tryMoveAndShake(MapLocation mapLocation,Integer tries) {
        if(tries>=5) return;
        if(utils.Current.I.canMove(mapLocation) ) {

            try {
                scoutEnemiesAround();
                utils.Current.I.move(mapLocation);
            } catch (GameActionException e) {
            }
        } else {
            System.out.print("I can not move"+ tries);
            mapLocation = new MapLocation(mapLocation.x+tries*10,mapLocation.y-tries);
            tryMoveAndScout(mapLocation, ++tries);
        }
    }



    private MapLocation checkForCorner(Direction direction) {
        Direction [] directions =breakDirectionToComponents(direction);
        MapLocation mapLocationHorizontal = checkForSide(directions[0]);
        MapLocation mapLocationVertical = checkForSide(directions[1]);


        if(directions[0].equals(Direction.getEast())) {
            if(mapLocationHorizontal !=null) {
                eastSide.put(utils.Current.I.getID(), mapLocationHorizontal);
            }
        } else if(directions[0].equals(Direction.getWest())) {
            if(mapLocationHorizontal !=null) {
                westSide.put(utils.Current.I.getID(), mapLocationHorizontal);
            }
        }

        if(directions[1].equals(Direction.getSouth())) {
            if(mapLocationVertical !=null) {
                southSide.put(utils.Current.I.getID(), mapLocationVertical);
            }
        } else if(directions[1].equals(Direction.getNorth())) {
            if(mapLocationVertical !=null) {
                northSide.put(utils.Current.I.getID(), mapLocationVertical);
            }
        }
        return null;
    }


    private MapLocation checkForSide(Direction direction) {
        MapLocation scoutLocation  = utils.Current.I.getLocation();
        Float scoutSensorRadiusStep = SCOUT.sensorRadius/5;

        MapLocation checkingLocation = scoutLocation;


        MapLocation previousCheckingLocation = scoutLocation;
        for(int i=0;i<5;i++) {
            checkingLocation=checkingLocation.subtract(direction, -1*scoutSensorRadiusStep);
            try {
                if(!utils.Current.I.onTheMap(checkingLocation)) {
                    return previousCheckingLocation;
                }
                previousCheckingLocation = checkingLocation;
            } catch (GameActionException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private Direction [] breakDirectionToComponents(Direction direction) {
        MapLocation scoutLocation = utils.Current.I.getLocation();
        MapLocation [] mapLocations = new MapLocation[2];
        Direction [] directions = new Direction[2];

        mapLocations[0] =  new MapLocation(scoutLocation.x+direction.getDeltaX(SCOUT.sensorRadius),scoutLocation.y);
        mapLocations[1] =  new MapLocation(scoutLocation.x,scoutLocation.y+ direction.getDeltaY(SCOUT.sensorRadius));

        directions[0] = new Direction(scoutLocation, mapLocations[0]);
        directions[1] = new Direction(scoutLocation, mapLocations[1]);
        return directions;
    }

    private MapLocation compineMapLocations(MapLocation firstMapLocation, MapLocation secondMapLocation) {
        MapLocation compinedMapLocation = new MapLocation(firstMapLocation.x, secondMapLocation.y);
        return compinedMapLocation;
    }


    private Direction compineDirections(Direction firstDirection, float firstDistance, Direction secondDirection, float secondDistance) {
        double angleOfDirectionsInRadiants = 0;
        float radiantsOfNewDirection;
        if(firstDirection.radians> secondDirection.radians) {
            angleOfDirectionsInRadiants = firstDirection.radians - secondDirection.radians;
            radiantsOfNewDirection= (float) Math.atan((Math.sin(angleOfDirectionsInRadiants)*firstDistance)/ (Math.cos(angleOfDirectionsInRadiants)*firstDistance+secondDistance));
        } else {
            angleOfDirectionsInRadiants = secondDirection.radians - firstDirection.radians ;
            radiantsOfNewDirection= (float) Math.atan((Math.sin(angleOfDirectionsInRadiants)*secondDistance)/ (Math.cos(angleOfDirectionsInRadiants)*secondDistance+firstDistance));
        }
        Direction compinedDirection = new Direction(radiantsOfNewDirection);
        return compinedDirection;
    }

    private float compineDistancesOfVectors(float vectorDistanceOne, float vectorDistanceTwo) {
        return (float) Math.sqrt(Math.pow(vectorDistanceOne, 2)+ Math.pow(vectorDistanceTwo, 2)+ 2*vectorDistanceOne*vectorDistanceTwo);
    }



    private void constuctCorners() {
        corners.put(Dir.NE(), new MapLocation(limits.get(Direction.getEast()), limits.get(Direction.getNorth())));
        corners.put(Dir.SE(), new MapLocation(limits.get(Direction.getEast()), limits.get(Direction.getSouth())));
        corners.put(Dir.SW(), new MapLocation(limits.get(Direction.getWest()), limits.get(Direction.getSouth())));
        corners.put(Dir.NW(), new MapLocation(limits.get(Direction.getWest()), limits.get(Direction.getNorth())));
    }


    private void scoutEnemiesAround(){
        for(RobotInfo robot: utils.Current.I.senseNearbyRobots(Current.I.getType().sensorRadius, Current.I.getTeam().opponent())) {
            robotsSeen.put(robot.getID(), robot);
        }
    }

    public void printEnemyLog(){
        System.out.println("");
        System.out.println("Enemies scouted");
        for(int i=0 ; i <=1; i++) {
            for(int j =0 ; j<=1 ; j++) {
                System.out.println("");
                System.out.println("Enemies seen on quarter("+i+", "+j+"):");
                for(RobotInfo robotInfo :quarters[i][j].getRobots()) {
                    System.out.println(robotInfo.toString());
                }
            }
        }
    }


    public void patrolQuarter(MapPiece quarter, boolean tryShake) throws GameActionException {


        while(!utils.Current.I.getLocation().isWithinDistance(quarter.getBottomRight(),5)) {
            if(tryMoveAndScout( quarter.getBottomRight(), 0)==-1) break;
            if(tryShake) shakeNearByTrees();
        }
        while(!utils.Current.I.getLocation().isWithinDistance(quarter.getTopRight(),5)) {
            if(tryMoveAndScout( quarter.getTopRight(), 0)==-1) break;
            if(tryShake) shakeNearByTrees();
        }
        while(!utils.Current.I.getLocation().isWithinDistance(quarter.getTopLeft(),5)) {
            if(tryMoveAndScout( quarter.getTopLeft(), 0)==-1) break;
            if(tryShake) shakeNearByTrees();
        }

        while(!utils.Current.I.getLocation().isWithinDistance(quarter.getBottomLeft(),5)) {
            if(tryMoveAndScout( quarter.getBottomLeft(), 0)==-1) break;
            if(tryShake) shakeNearByTrees();
        }

    }

    public void shakeNearByTrees() throws GameActionException {
        TreeInfo[] nearbyTrees = Current.I.senseNearbyTrees();
        for(TreeInfo tree: nearbyTrees) {
            if(Current.I.canShake(tree.getID())) {
                System.out.println("Shaked the tree in location "+ tree.getLocation());
                Current.I.shake(tree.getID());
            }
        }
    }

}
