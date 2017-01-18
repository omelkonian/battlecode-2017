package sc.utils

import battlecode.common._

/**
  * Scala compatibility RobotController API.
  */
class EnrichedRC(rc: RobotController) {
  // Extra
  def sensorRadius: Float = rc.getType.sensorRadius
  def strideRadius: Float = rc.getType.strideRadius

  // Overriden
  def canFirePentadShot: Boolean = rc.canFirePentadShot

  def resign(): Unit = resign()

  def getType: RobotType = rc.getType

  def hireGardener(direction: Direction): Unit = rc hireGardener direction

  def move(direction: Direction): Unit = rc move direction

  def move(direction: Direction, v: Float): Unit = rc move (direction, v)

  def move(mapLocation: MapLocation): Unit = rc move mapLocation

  def senseNearbyBullets(): Seq[BulletInfo] = rc.senseNearbyBullets() : Seq[BulletInfo]

  def senseNearbyBullets(v: Float): Seq[BulletInfo] = rc senseNearbyBullets v : Seq[BulletInfo]

  def senseNearbyBullets(mapLocation: MapLocation, v: Float): Seq[BulletInfo] =
    rc senseNearbyBullets (mapLocation, v) : Seq[BulletInfo]

  def getID: Int = rc.getID

  def canSenseRobot(i: Int): Boolean = rc canSenseRobot i

  def canWater(mapLocation: MapLocation): Boolean = rc canWater mapLocation

  def canWater(i: Int): Boolean = rc canWater i

  def canWater: Boolean = rc.canWater

  def hasRobotBuildRequirements(robotType: RobotType): Boolean =
    rc hasRobotBuildRequirements robotType

  def hasMoved: Boolean = rc.hasMoved

  def getRoundLimit: Int = rc.getRoundLimit

  def canPlantTree(direction: Direction): Boolean = rc canPlantTree direction

  def canHireGardener(direction: Direction): Boolean = rc canHireGardener direction

  def getHealth: Float = rc.getHealth

  def disintegrate(): Unit = rc.disintegrate()

  def senseNearbyRobots(): Seq[RobotInfo] = rc.senseNearbyRobots() : Seq[RobotInfo]

  def senseNearbyRobots(v: Float): Seq[RobotInfo] = rc senseNearbyRobots v : Seq[RobotInfo]

  def senseNearbyRobots(v: Float, team: Team): Seq[RobotInfo] =
    rc senseNearbyRobots (v, team) : Seq[RobotInfo]

  def senseNearbyRobots(mapLocation: MapLocation, v: Float, team: Team): Seq[RobotInfo] =
    rc senseNearbyRobots (mapLocation, v, team) : Seq[RobotInfo]

  def canSenseLocation(mapLocation: MapLocation): Boolean = rc canSenseLocation mapLocation

  def setIndicatorLine(mapLocation: MapLocation, mapLocation1: MapLocation, i: Int, i1: Int, i2: Int): Unit =
    rc setIndicatorLine (mapLocation, mapLocation1, i, i1, i2)

  def isLocationOccupiedByTree(mapLocation: MapLocation): Boolean = rc isLocationOccupiedByTree mapLocation

  def senseTreeAtLocation(mapLocation: MapLocation): TreeInfo = rc senseTreeAtLocation mapLocation

  def strike(): Unit = rc.strike()

  def getTeamMemory: Seq[Long] = rc.getTeamMemory : Seq[Long]

  def chop(mapLocation: MapLocation): Unit = rc chop mapLocation

  def chop(i: Int): Unit = rc chop i

  def readBroadcast(i: Int): Int = rc readBroadcast i

  def canFireSingleShot: Boolean = rc.canFireSingleShot

  def setIndicatorDot(mapLocation: MapLocation, i: Int, i1: Int, i2: Int): Unit = rc setIndicatorDot (mapLocation, i, i1, i2)

  def onTheMap(mapLocation: MapLocation): Boolean = rc onTheMap mapLocation

  def onTheMap(mapLocation: MapLocation, v: Float): Boolean = rc onTheMap (mapLocation, v)

  def hasAttacked: Boolean = rc.hasAttacked

  def canSenseAllOfCircle(mapLocation: MapLocation, v: Float): Boolean = rc canSenseAllOfCircle (mapLocation, v)

  def getRobotCount: Int = rc.getRobotCount

  def canSensePartOfCircle(mapLocation: MapLocation, v: Float): Boolean = rc canSensePartOfCircle (mapLocation, v)

  def isCircleOccupied(mapLocation: MapLocation, v: Float): Boolean = rc isCircleOccupied (mapLocation, v)

  def senseRobot(i: Int): RobotInfo = rc senseRobot i

  def getControlBits: Long = rc.getControlBits

  def getTeamVictoryPoints: Int = rc.getTeamVictoryPoints

  def senseNearbyTrees(): Seq[TreeInfo] = rc.senseNearbyTrees() : Seq[TreeInfo]

  def senseNearbyTrees(v: Float): Seq[TreeInfo] = rc senseNearbyTrees v : Seq[TreeInfo]

  def senseNearbyTrees(v: Float, team: Team): Seq[TreeInfo] =
    rc senseNearbyTrees (v, team) : Seq[TreeInfo]

  def senseNearbyTrees(mapLocation: MapLocation, v: Float, team: Team): Seq[TreeInfo] =
    rc senseNearbyTrees (mapLocation, v, team) : Seq[TreeInfo]

  def setTeamMemory(i: Int, l: Long): Unit = rc setTeamMemory (i, l)

  def setTeamMemory(i: Int, l: Long, l1: Long): Unit = rc setTeamMemory (i, l, l1)

  def buildRobot(robotType: RobotType, direction: Direction): Unit = rc buildRobot (robotType, direction)

  def senseTree(i: Int): TreeInfo = rc senseTree i

  def canMove(direction: Direction): Boolean = rc canMove direction

  def canMove(direction: Direction, v: Float): Boolean = rc canMove (direction, v)

  def canMove(mapLocation: MapLocation): Boolean = rc canMove mapLocation

  def getInitialArchonLocations(team: Team): Seq[MapLocation] =
    rc getInitialArchonLocations team: Seq[MapLocation]

  def senseRobotAtLocation(mapLocation: MapLocation): RobotInfo = rc senseRobotAtLocation mapLocation

  def isLocationOccupiedByRobot(mapLocation: MapLocation): Boolean = rc isLocationOccupiedByRobot mapLocation

  def firePentadShot(direction: Direction): Unit = rc firePentadShot direction

  def getAttackCount: Int = rc.getAttackCount

  def getTreeCount: Int = rc.getTreeCount

  def canSenseBullet(i: Int): Boolean = rc canSenseBullet i

  def getTeam: Team = rc.getTeam

  def broadcast(i: Int, i1: Int): Unit = rc broadcast (i, i1)

  def hasTreeBuildRequirements: Boolean = rc.hasTreeBuildRequirements

  def water(mapLocation: MapLocation): Unit = rc water mapLocation

  def water(i: Int): Unit = rc water i

  def donate(v: Float): Unit = rc donate v

  def fireSingleShot(direction: Direction): Unit = rc fireSingleShot direction

  def shake(mapLocation: MapLocation): Unit = rc shake mapLocation

  def shake(i: Int): Unit = rc shake i

  def canSenseTree(i: Int): Boolean = rc canSenseTree i

  def getRoundNum: Int = rc.getRoundNum

  def isCircleOccupiedExceptByThisRobot(mapLocation: MapLocation, v: Float): Boolean =
    rc isCircleOccupiedExceptByThisRobot (mapLocation, v)

  def fireTriadShot(direction: Direction): Unit = rc fireTriadShot direction

  def canFireTriadShot: Boolean = rc.canFireTriadShot

  def getBuildCooldownTurns: Int = rc.getBuildCooldownTurns

  def senseBroadcastingRobotLocations(): Seq[MapLocation] =
    rc.senseBroadcastingRobotLocations(): Seq[MapLocation]

  def senseBullet(i: Int): BulletInfo = rc senseBullet i

  def getMoveCount: Int = rc.getMoveCount

  def getLocation: MapLocation = rc.getLocation

  def getTeamBullets: Float = rc.getTeamBullets

  def canSenseRadius(v: Float): Boolean = rc canSenseRadius v

  def canBuildRobot(robotType: RobotType, direction: Direction): Boolean = rc canBuildRobot (robotType, direction)

  def canStrike: Boolean = rc.canStrike

  def plantTree(direction: Direction): Unit = rc plantTree direction

  def canInteractWithTree(mapLocation: MapLocation): Boolean = rc canInteractWithTree mapLocation

  def canInteractWithTree(i: Int): Boolean = rc canInteractWithTree i

  def isBuildReady: Boolean = rc.isBuildReady

  def isLocationOccupied(mapLocation: MapLocation): Boolean = rc isLocationOccupied mapLocation

  def canChop(mapLocation: MapLocation): Boolean = rc canChop mapLocation

  def canChop(i: Int): Boolean = rc canChop i

  def canShake(mapLocation: MapLocation): Boolean = rc canShake mapLocation

  def canShake(i: Int): Boolean = rc canShake i

  def canShake: Boolean = rc.canShake

}
