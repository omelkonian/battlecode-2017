package units.soldiers;
import battlecode.common.*;
import units.RobotUnit;
import utils.Current;
import utils.Evasion;
import utils.Movement;

public class SimpleEvaderSoldier implements RobotUnit {
	/**
	 * A simple evader soldier, who:
	 * If there are nearby bullets, he tries to evade them.
	 * Else, if there are nearby enemies, moves towards them and shots them.
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
		try {
			RobotInfo[] enemyNearbyRobots;
			BulletInfo[] nearbyBullets = robotController.senseNearbyBullets();
			Direction dirToEvade = Evasion.tryEvadeBullets(nearbyBullets);
			if (dirToEvade != null) {
				// No need to tryMove, because we checked if canMove in the tryEvadeBullets
				robotController.move((dirToEvade));
			};
			
			enemyNearbyRobots = robotController.senseNearbyRobots(-1, enemyTeam);
			if (enemyNearbyRobots.length > 0) {
		        Direction enemyRobotTargetDirection = robotController.getLocation().directionTo(enemyNearbyRobots[0].location);
		        if (!robotController.hasMoved()) {
		        	Movement.tryMove(enemyRobotTargetDirection);
		        }
			    if (robotController.canFireSingleShot()) {
			    // ...Then fire a bullet in the direction of the enemy.
			    	robotController.fireSingleShot(enemyRobotTargetDirection);
			    } 
			} else {
				move();
			}				
							
		} catch (Exception e) {
          System.out.println("SimpleEvaderSoldier Exception");
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

