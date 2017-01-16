package units.soldiers;
import battlecode.common.*;
import units.RobotUnit;
import utils.Current;
import utils.Movement;

public class SimpleAttackerSoldier implements RobotUnit {
	/**
	 * A simple soldier, who:
	 * If there are nearby enemies, moves towards them and shots them.
	 * Else, he moves (randomly or to a target direction if it has been set TODO how to set it)
	 */
	Direction targetDirection;
	MapLocation ourArchonLocation;
	Direction lastRandomMoveDirection;
	Team ourTeam;
	Team enemyTeam;
	RobotController robotController;
	
	public void run() throws GameActionException {
		robotController = Current.I;
		ourTeam = robotController.getTeam();
		enemyTeam = robotController.getTeam().opponent();
	    while (true) {
	        runStep();	    	
	    }
	}

	@Override
	public void runStep() throws GameActionException {
		// TODO Auto-generated method stub
		
		try {
			RobotInfo[] enemyNearbyRobots = robotController.senseNearbyRobots(-1, enemyTeam);
			if (enemyNearbyRobots.length > 0) {
	        	Direction enemyRobotTargetDirection = robotController.getLocation().directionTo(enemyNearbyRobots[0].location);

		        if (robotController.canFireSingleShot()) {
		           // ...Then fire a bullet in the direction of the enemy.
		            Movement.tryMove(enemyRobotTargetDirection);
		            robotController.fireSingleShot(enemyRobotTargetDirection);
		        } else {
		        	move(); // Move randomly to avoid the enemy bullet
		        }
			} else {
				move();
			}
			
		} catch (Exception e) {
          System.out.println("SimpleAttackerSoldier Exception");
          e.printStackTrace();
      }
	}
	
	

	/**
	 * Move the robot. If a general target is available, move towards there.
	 * Else, try to keep previous movement direction
	 * @throws GameActionException
	 */
	public void move() throws GameActionException{
		if (targetDirection != null ) {
			Movement.tryMove(targetDirection);
		} else if (lastRandomMoveDirection != null) {
			Movement.tryMove(lastRandomMoveDirection);
		} else {
			lastRandomMoveDirection = Movement.randomDirection();
			Movement.tryMove(lastRandomMoveDirection);
		}
	}
	
}

