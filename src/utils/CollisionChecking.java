package utils;
import battlecode.common.BulletInfo;
import battlecode.common.Direction;
import battlecode.common.MapLocation;
import utils.Current;


public class CollisionChecking {


    /**
    * Returns weather a given bullet is on a collision course with an object. 
    * Doesn't take into account objects between the bullet and this robot.
    *
    * @param bullet The bullet in question
    * @param objectLocation The object's location
    * @param objectRadious  The object's radius
    * @return True if the line of the bullet's path intersects with this robot's current position.
    */
  public static boolean willColideWithObject(BulletInfo bullet, MapLocation objectLocation, float objectRadius){

    // Get relevant bullet information
	Direction propagationDirection  = bullet.dir;
	MapLocation bulletLocation  = bullet.location;

    // Calculate bullet relations to this robot
	Direction directionToRobot  = bulletLocation.directionTo(objectLocation);
	float distToRobot  = bulletLocation.distanceTo(objectLocation);
	float theta  = propagationDirection.radiansBetween(directionToRobot);

    // If theta > 90 degrees, then the bullet is traveling away from us and we can break early
    if (Math.abs(theta) > Math.PI/2)
      return false;

    // distToRobot is our hypotenuse, theta is our angle, and we want to know this length of the opposite leg.
    // This is the distance of a line that goes from myLocation and intersects perpendicularly with propagationDirection.
    // This corresponds to the smallest radius circle centered at our location that would intersect with the
    // line that is the path of the bullet.
    float perpendicularDist = (float) Math.abs(distToRobot * Math.sin(theta)) ;  // soh cah toa :)

    return perpendicularDist <= objectRadius;
    
  }
  
  
  public static boolean willColideWithObject(BulletInfo[] bullets, MapLocation objectLocation, float objectRadius){
	  for(int i =0; i < bullets.length; i++) {
		  if (willColideWithObject(bullets[i], objectLocation, objectRadius)) {
			  return true;
		  }
	  }
	  return false;
  }

    /**
    * Return true if the given bullet is on a collision
    * course with the current robot. Doesn't take into account objects between the bullet and this robot.
    *
    * @param bullet The bullet in question
    * @return True if the line of the bullet's path intersects with this robot's current position.
    */
  public static boolean willCollideWithMe(BulletInfo bullet) {
    return willColideWithObject(bullet, Current.Here(), Current.MyType().bodyRadius);
  }
  
  public static boolean willCollideWithMe(BulletInfo[] bullets) {
    return willColideWithObject(bullets, Current.Here(), Current.MyType().bodyRadius);
  }

  
  /**
   * Return when (in how many rounds) the bullet will collide with the given object
    * @param bullet The bullet in question
    * @param objectLocation The object's location
    * @param objectRadious  The object's radius
    * @return How many rounds until bullet collides with this object (-1 means never)
   */
  public static int whenWillColideWithObject(BulletInfo bullet, MapLocation objectLocation, float objectRadius){
     if (willColideWithObject(bullet, objectLocation, objectRadius)) {
       return (int) (Math.floor(bullet.getLocation().distanceTo(objectLocation) / bullet.speed)); 
    }
     return -1;
  }
  
  public static int whenWillColideWithObject(BulletInfo[] bullets, MapLocation objectLocation, float objectRadius){
	  int min = -1;
	  int round;
	  
	  for (int i = 1; i< bullets.length; i++) {
		  round = whenWillColideWithObject(bullets[i], objectLocation, objectRadius) ;
		  min = round < min ? round : min;
	  }
	  return min;
  }
  
  public static int whenWillColideWithObject(BulletInfo bullet, MapLocation objectLocation, float objectRadius, boolean skipCollisionCheck){
	  if (skipCollisionCheck) {
		  return (int) (Math.floor(bullet.getLocation().distanceTo(objectLocation) / bullet.speed)); 
	  }
	  return whenWillColideWithObject(bullet, objectLocation, objectRadius);
  }
  
    /**
    * Return when (in how many rounds) the bullet will collide with the current robot
    * @param bullet The bullet in question
    * @param objectLocation The object's location
    * @param objectRadious  The object's radius
    * @return A float representing in how many rounds bullet will collide with object
   */
  public static int whenWillColideWithMe(BulletInfo bullet){
    return whenWillColideWithObject(bullet, Current.Here(), Current.MyType().bodyRadius);
  }  
  
  public static int whenWillColideWithMe(BulletInfo bullet, boolean skipCollisionCheck){
	    return whenWillColideWithObject(bullet, Current.Here(), Current.MyType().bodyRadius, skipCollisionCheck);
	  }    
}
