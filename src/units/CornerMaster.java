package units;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;

import static battlecode.common.RobotType.SCOUT;

/**
 * Created by gdimopou on 16/01/2017.
 */
public class CornerMaster implements RobotUnit {

    private HashMap<Integer,MapLocation> horizontalSide = null;
    private HashMap<Integer,MapLocation> verticalSide = null;
    private HashMap<Integer,Direction> assignedDirections = null;
    private ArrayList<Direction> directionsToCheck = null;
    private HashMap<Integer, MapLocation> corners = null;
    private Integer directionsBeenAssigned;

    @Override
    public void run() throws GameActionException {
        horizontalSide = new HashMap<Integer,MapLocation>();
        verticalSide = new HashMap<Integer,MapLocation>();
        assignedDirections = new HashMap<Integer,Direction>();
        corners = new HashMap<Integer, MapLocation>() ;
        directionsToCheck = new ArrayList<Direction>();
        directionsToCheck.add(Direction.getEast());
        directionsToCheck.add(Direction.getNorth());
        directionsToCheck.add(Direction.getWest());
        directionsToCheck.add(Direction.getSouth());
        System.out.println(directionsToCheck.toString());
        directionsBeenAssigned = 0;
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
        Integer scoutNumber = 0;
        if(!assignedDirections.keySet().contains(utils.Current.I.getID())) {
            if(directionsBeenAssigned<4) {
                assignedDirections.put(utils.Current.I.getID(), directionsToCheck.get(directionsBeenAssigned));
            }
            System.out.println("SCOUT WITH ID "+utils.Current.I.getID()+" HAS DIRECTION TO "+assignedDirections.get(utils.Current.I.getID()));
            directionsBeenAssigned++;
        }
        MapLocation corner = checkForCorner(assignedDirections.get(utils.Current.I.getID()));
        if(corner !=null) {
            System.out.println("CORNER FOUND "+corner.x+","+corner.y);
            corners.put(utils.Current.I.getID(), corner);
            verticalSide.put(utils.Current.I.getID(), null);
            horizontalSide.put(utils.Current.I.getID(), null);
            if(directionsBeenAssigned<4) {
                assignedDirections.put(utils.Current.I.getID(), directionsToCheck.get(directionsBeenAssigned));
            }
        }

        if(verticalSide.get(utils.Current.I.getID())!=null) {
            if(directionsBeenAssigned<4) {
                assignedDirections.put(utils.Current.I.getID(), directionsToCheck.get(directionsBeenAssigned));
            }
        }
        if( horizontalSide.get(utils.Current.I.getID())!=null) {
            if(directionsBeenAssigned<4) {
                assignedDirections.put(utils.Current.I.getID(), directionsToCheck.get(directionsBeenAssigned));
            }
        }

        tryMove(assignedDirections.get(utils.Current.I.getID()),0);
    }


    private void tryMove(Direction direction,Integer tries) {
        if(tries>=3) return;
        System.out.println("Try :"+tries);
        if(utils.Current.I.canMove(direction)) {
            try {
                utils.Current.I.move(direction);
            } catch (GameActionException e) {
                e.printStackTrace();
            }
        } else {
            direction = new Direction((float) (direction.radians + Math.pow(-1,tries%2)*(Math.PI/4)*(tries+1)));
            tryMove(direction, ++tries);
        }
    }




    private MapLocation checkForCorner(Direction direction) {
        Direction [] directions =breakDirectionToComponents(direction);
        MapLocation mapLocationHorizontal = checkForSide(directions[0]);
        MapLocation mapLocationVertical = checkForSide(directions[1]);

        if(mapLocationHorizontal !=null) {
            System.out.println("Found Horizontal:"+ mapLocationHorizontal.x+","+mapLocationHorizontal.y);
            horizontalSide.put(utils.Current.I.getID(), mapLocationHorizontal);
        }

        if(mapLocationVertical !=null){
            System.out.println("Found Vertical:"+ mapLocationVertical.x+","+mapLocationVertical.y);
            verticalSide.put(utils.Current.I.getID(), mapLocationVertical);
        }

        if(verticalSide.get(utils.Current.I.getID())!=null && horizontalSide.get(utils.Current.I.getID())!=null) {
            return compineMapLocations(mapLocationHorizontal, mapLocationVertical);
        }

        return null;
    }


    private MapLocation checkForSide(Direction direction) {
        MapLocation scoutLocation  = utils.Current.I.getLocation();
        Float scoutSensorRadiusStep = SCOUT.sensorRadius/5;

        MapLocation checkingLocation = scoutLocation;


        MapLocation previousCheckingLocation = scoutLocation;
        for(int i=0;i<5;i++) {
            System.out.println("NOW CHECKING LOCATION -> ("+checkingLocation.x+" ,"+checkingLocation.y+")");
            checkingLocation=checkingLocation.add(direction, -1*scoutSensorRadiusStep);
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

        mapLocations[0] =  new MapLocation(direction.getDeltaX(SCOUT.sensorRadius),scoutLocation.y);
        mapLocations[1] =  new MapLocation(scoutLocation.x, direction.getDeltaY(SCOUT.sensorRadius));

        directions[0] = new Direction(scoutLocation, mapLocations[0]);
        directions[1] = new Direction(scoutLocation, mapLocations[1]);
        return directions;
    }

    private MapLocation compineMapLocations(MapLocation firstMapLocation, MapLocation secondMapLocation) {
        MapLocation scoutLocation = utils.Current.I.getLocation();
        Direction [] directions = new Direction[2];
        directions[0] = new Direction(scoutLocation, firstMapLocation);
        directions[1] = new Direction(scoutLocation, secondMapLocation);
        return scoutLocation.add(
                compineDirections(directions[0], scoutLocation.distanceTo(firstMapLocation),directions[1],scoutLocation.distanceTo(secondMapLocation)),
                compineDistancesOfVectors(scoutLocation.distanceTo(firstMapLocation),scoutLocation.distanceTo(secondMapLocation)));
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

        return new Direction(radiantsOfNewDirection);
    }

    private float compineDistancesOfVectors(float vectorDistanceOne, float vectorDistanceTwo) {
        return (float) Math.sqrt(Math.pow(vectorDistanceOne, 2)+ Math.pow(vectorDistanceTwo, 2)+ 2*vectorDistanceOne*vectorDistanceTwo);
    }




}
