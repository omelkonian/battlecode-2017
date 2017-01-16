package utils

import battlecode.common._

/**
  * Scala compatibility RobotController API.
  */
class EnrichedRC(rc: RobotController) {
  // Extra
  def sensorRadius = rc.getType.sensorRadius
  def strideRadius = rc.getType.strideRadius

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

  def getHealth: Float = ???

  def disintegrate(): Unit = ???

  def senseNearbyRobots(): Seq[RobotInfo] = rc.senseNearbyRobots() : Seq[RobotInfo]

  def senseNearbyRobots(v: Float): Seq[RobotInfo] = rc senseNearbyRobots v : Seq[RobotInfo]

  def senseNearbyRobots(v: Float, team: Team): Seq[RobotInfo] =
    rc senseNearbyRobots (v, team) : Seq[RobotInfo]

  def senseNearbyRobots(mapLocation: MapLocation, v: Float, team: Team): Seq[RobotInfo] =
    rc senseNearbyRobots (mapLocation, v, team) : Seq[RobotInfo]

  def canSenseLocation(mapLocation: MapLocation): Boolean = ???

  def setIndicatorLine(mapLocation: MapLocation, mapLocation1: MapLocation, i: Int, i1: Int, i2: Int): Unit = ???

  def isLocationOccupiedByTree(mapLocation: MapLocation): Boolean = rc isLocationOccupiedByTree mapLocation

  def senseTreeAtLocation(mapLocation: MapLocation): TreeInfo = ???

  def strike(): Unit = ???

  def getTeamMemory: Seq[Long] = rc.getTeamMemory : Seq[Long]

  def chop(mapLocation: MapLocation): Unit = ???

  def chop(i: Int): Unit = ???

  def readBroadcast(i: Int): Int = rc readBroadcast i

  def canFireSingleShot: Boolean = ???

  def setIndicatorDot(mapLocation: MapLocation, i: Int, i1: Int, i2: Int): Unit = ???

  def onTheMap(mapLocation: MapLocation): Boolean = rc onTheMap mapLocation

  def onTheMap(mapLocation: MapLocation, v: Float): Boolean = rc onTheMap (mapLocation, v)

  def hasAttacked: Boolean = rc.hasAttacked

  def canSenseAllOfCircle(mapLocation: MapLocation, v: Float): Boolean = ???

  def getRobotCount: Int = ???

  def canSensePartOfCircle(mapLocation: MapLocation, v: Float): Boolean = ???

  def isCircleOccupied(mapLocation: MapLocation, v: Float): Boolean = ???

  def senseRobot(i: Int): RobotInfo = ???

  def getControlBits: Long = ???

  def getTeamVictoryPoints: Int = ???

  def senseNearbyTrees(): Seq[TreeInfo] = rc.senseNearbyTrees() : Seq[TreeInfo]

  def senseNearbyTrees(v: Float): Seq[TreeInfo] = rc senseNearbyTrees v : Seq[TreeInfo]

  def senseNearbyTrees(v: Float, team: Team): Seq[TreeInfo] =
    rc senseNearbyTrees (v, team) : Seq[TreeInfo]

  def senseNearbyTrees(mapLocation: MapLocation, v: Float, team: Team): Seq[TreeInfo] =
    rc senseNearbyTrees (mapLocation, v, team) : Seq[TreeInfo]

  def setTeamMemory(i: Int, l: Long): Unit = ???

  def setTeamMemory(i: Int, l: Long, l1: Long): Unit = ???

  def buildRobot(robotType: RobotType, direction: Direction): Unit = rc buildRobot (robotType, direction)

  def senseTree(i: Int): TreeInfo = ???

  def canMove(direction: Direction): Boolean = rc canMove direction

  def canMove(direction: Direction, v: Float): Boolean = rc canMove (direction, v)

  def canMove(mapLocation: MapLocation): Boolean = rc canMove mapLocation

  def getInitialArchonLocations(team: Team): Seq[MapLocation] =
    rc getInitialArchonLocations team: Seq[MapLocation]

  def senseRobotAtLocation(mapLocation: MapLocation): RobotInfo = ???

  def isLocationOccupiedByRobot(mapLocation: MapLocation): Boolean = ???

  def firePentadShot(direction: Direction): Unit = ???

  def getAttackCount: Int = ???

  def getTreeCount: Int = ???

  def canSenseBullet(i: Int): Boolean = ???

  def getTeam: Team = rc.getTeam

  def broadcast(i: Int, i1: Int): Unit = rc broadcast (i, i1)

  def hasTreeBuildRequirements: Boolean = ???

  def water(mapLocation: MapLocation): Unit = rc water mapLocation

  def water(i: Int): Unit = rc water i

  def donate(v: Float): Unit = rc donate v

  def fireSingleShot(direction: Direction): Unit = ???

  def shake(mapLocation: MapLocation): Unit = ???

  def shake(i: Int): Unit = ???

  def canSenseTree(i: Int): Boolean = ???

  def getRoundNum: Int = ???

  def isCircleOccupiedExceptByThisRobot(mapLocation: MapLocation, v: Float): Boolean =
    rc isCircleOccupiedExceptByThisRobot (mapLocation, v)

  def fireTriadShot(direction: Direction): Unit = ???

  def canFireTriadShot: Boolean = ???

  def getBuildCooldownTurns: Int = ???

  def senseBroadcastingRobotLocations(): Seq[MapLocation] =
    rc.senseBroadcastingRobotLocations(): Seq[MapLocation]

  def senseBullet(i: Int): BulletInfo = ???

  def getMoveCount: Int = ???

  def getLocation: MapLocation = rc.getLocation

  def getTeamBullets: Float = rc.getTeamBullets

  def canSenseRadius(v: Float): Boolean = ???

  def canBuildRobot(robotType: RobotType, direction: Direction): Boolean = rc canBuildRobot (robotType, direction)

  def canStrike: Boolean = rc.canStrike

  def plantTree(direction: Direction): Unit = rc plantTree direction

  def canInteractWithTree(mapLocation: MapLocation): Boolean = rc canInteractWithTree mapLocation

  def canInteractWithTree(i: Int): Boolean = rc canInteractWithTree i

  def isBuildReady: Boolean = ???

  def isLocationOccupied(mapLocation: MapLocation): Boolean = ???

  def canChop(mapLocation: MapLocation): Boolean = ???

  def canChop(i: Int): Boolean = ???

  def canShake(mapLocation: MapLocation): Boolean = ???

  def canShake(i: Int): Boolean = ???

  def canShake: Boolean = ???

  /**
  NOT WORKING POLYMORPHISM
  ========================
  val obstacles: Array[BodyInfo] = I.senseNearbyTrees() ++ I.senseNearbyRobots()
  val all: Array[BodyInfo] = for (tree <- trees) yield tree

  WORKING ITERATION
  =================
  val treeInfo: BodyInfo = new TreeInfo(0, I.getTeam, Here, 0f, 0f, 0, MyType)
  for (tree: BodyInfo <- Seq(treeInfo))
    System.out.println(tree)

  val trees: Seq[BodyInfo] = for (tree: BodyInfo <- Seq(treeInfo)) yield tree
  System.out.println(trees)

  WORKING IMPLICIT
  ================
  def testImplicit(implicit info: BodyInfo): Unit = System.out.println(info.getID)

  implicit val treeInfo: BodyInfo = new TreeInfo(0, I.getTeam, Here, 0f, 0f, 0, MyType)
  testImplicit

  WORKING POLYMORPHISM
  ====================
  def testPolymorphism[T](array: Array[T]): Boolean = true

  val trees: Array[TreeInfo] = I.senseNearbyTrees()
  assert(testPolymorphism(trees))

  waitAndThen2[Direction](
    init = N,
    condition = _ => true,
    action = _ => System.out.println("action"),
    variance = d => d
  )
  val treeInfo: BodyInfo = new TreeInfo(0, I.getTeam, Here, 0f, 0f, 0, MyType)
  waitAndThen2[BodyInfo](
    init = treeInfo,
    condition = _ => true,
    action = _ => System.out.println("action"),
    variance = d => d
  )

  WORKING COMPLEX
  ===============

  assert(
    filterSource[TreeInfo](
      source = for (id <- 0 to 3)
        yield new TreeInfo(id, MyTeam, Here, 0f, 0f, 0, MyType),
      filters = Seq(
        _.ID > 1,
        _.getTeam == MyTeam,
        _.containedRobot == MyType
      ),
      notFilters = Seq(
        _.ID > 3
      )
    ).head.ID == 2
  )
    **/
}
