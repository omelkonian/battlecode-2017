package utils;
import battlecode.common.MapLocation;
import battlecode.common.Direction;
import battlecode.common.BulletInfo;
import utils.Current;
import utils.CollisionChecking;

public class Evasion {
	
	// TODO: (In the future) Take into account how many rounds you have to avoid a bullets
	
	//
	public static float DEFAULT_DEGREE_STEP = 10;

	/**
	 * Calculate end location, if robot moves fully to that direction
	 * @param direction The direction of movement
	 * @return the MapLocation where the robot will be if it moves fully to that direction for one round
	 */
	public static MapLocation movementEndLocation(Direction direction) {
		MapLocation startLocation = Current.Here();
		
		// A robot can move within its stride Radius. Therefore its "speed" is its stride radius
		float travelledX = direction.getDeltaX(Current.MyType().bodyRadius);
		float travelledY = direction.getDeltaY(Current.MyType().bodyRadius);
		return new MapLocation(startLocation.x + travelledX, startLocation.y + travelledY);
	}
	
	/**
	 * Try to evade the given bullet.
	 * @param bullet
	 * @return The direction towards which full movement (1 turn) will evade the bullet.
	 *         null if bullet cannot be evaded in one turn. 
	 */
	public static Direction tryEvadeBullet(BulletInfo bullet) {
		// We do not have a wanted direction, so try to go perpendicular to the bullet's direction 
		return tryEvadeBullet(bullet, bullet.dir.rotateRightDegrees(90), DEFAULT_DEGREE_STEP );
	}
	
	/**
	 * Try to evade the given bullet, by trying to move towards wantedDirection if possible.
	 * @param bullet
	 * @param wantedDirection The preferred direction. We will try it first, and then try to stay as close as possible.
	 * @param angleStepDegrees The step with which we try new directions (0-180). Smaller means better chances, but bigger cost.
	 * @return The direction towards which full movement (1 turn) will evade the bullet.
	 *         null if bullet cannot be evaded in one turn. 
	 */
	public static Direction tryEvadeBullet(BulletInfo bullet, Direction wantedDirection, float angleStepDegrees) {
		Direction tryDirection = wantedDirection;
		float robotSpeed = Current.MyType().bodyRadius;
		
		if (Current.I.canMove(tryDirection, robotSpeed) && CollisionChecking.willColideWithObject(bullet, movementEndLocation(tryDirection), Current.MyType().bodyRadius)) {
			return wantedDirection;
		}
		
		int currentDegreesOffset = 0;
		while (currentDegreesOffset <= 180) {
			tryDirection = wantedDirection.rotateLeftDegrees(currentDegreesOffset);
			if (Current.I.canMove(tryDirection, robotSpeed) && CollisionChecking.willColideWithObject(bullet, movementEndLocation(tryDirection), Current.MyType().bodyRadius)) {
				return tryDirection;
			}
			tryDirection = wantedDirection.rotateRightDegrees(currentDegreesOffset);
			if (Current.I.canMove(tryDirection, robotSpeed) && CollisionChecking.willColideWithObject(bullet, movementEndLocation(tryDirection), Current.MyType().bodyRadius)) {
				return tryDirection;
			}
			
			currentDegreesOffset += angleStepDegrees;
		}
		
		// We didn't manage to find a direction to avoid the bullet in one round.
		return null;
	}
	
	
	/**
	 * Try to evade all the bullets.
	 * @param bullet
	 * @return The direction towards which full movement (1 turn) will evade the bullet.
	 *         null if bullets cannot be evaded in one turn. 
	 */
	public static Direction tryEvadeBullets(BulletInfo[] bullets) {
		// We do not have a wanted direction, so try to go perpendicular to the first bullet's direction 
		return tryEvadeBullets(bullets, bullets[0].dir.rotateRightDegrees(90), DEFAULT_DEGREE_STEP );
	}
	
	/**
	 * Try to evade all the bullets by trying to move towards wantedDirection if possible.
	 * @param bullets
	 * @param wantedDirection The preferred direction. We will try it first, and then try to stay as close as possible.
	 * @param angleStepDegrees The step with which we try new directions (0-180). Smaller means better chances, but bigger cost.
	 * @return The direction towards which full movement (1 turn) will evade the bullets.
	 *         null if bullets cannot be evaded in one turn. 
	 */
	public static Direction tryEvadeBullets(BulletInfo[] bullets, Direction wantedDirection, float angleStepDegrees) {
		Direction tryDirection = wantedDirection;
		
		if (CollisionChecking.willColideWithObject(bullets, movementEndLocation(tryDirection), Current.MyType().bodyRadius)) {
			return wantedDirection;
		}
		
		int currentDegreesOffset = 0;
		while (currentDegreesOffset <= 180) {
			tryDirection = wantedDirection.rotateLeftDegrees(currentDegreesOffset);
			if (CollisionChecking.willColideWithObject(bullets, movementEndLocation(tryDirection), Current.MyType().bodyRadius)) {
				return tryDirection;
			}
			tryDirection = wantedDirection.rotateRightDegrees(currentDegreesOffset);
			if (CollisionChecking.willColideWithObject(bullets, movementEndLocation(tryDirection), Current.MyType().bodyRadius)) {
				return tryDirection;
			}
			
			currentDegreesOffset += angleStepDegrees;
		}
		
		// We didn't manage to find a direction to avoid the bullet in one round.
		return null;
	}

	
}
