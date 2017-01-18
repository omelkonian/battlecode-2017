package sc.utils

import battlecode.common.Direction
import j.utils.Constants._

object Dir {
  var E = new Direction(0.0f)
  var N = new Direction(PI/2)
  var W = new Direction(PI)
  var S = new Direction(3 * PI/2)
  var NE = new Direction(PI/4)
  var NW = new Direction(3 * PI/4)
  var SE = new Direction(7 * PI/2)
  var SW = new Direction(5 * PI/2)

  var dirs = Vector(N, E, S, W, NE, NW, SE, SW)

  // Average of a list of directions
  def avgDir(dirs : Array[Float]) : Float  = {
    return dirs(0)
//    return Math.atan2(dirs.map(d => Math.sin(d)).sum / dirs.length,
//                      dirs.map(d => Math.cos(d)).sum / dirs.length).toFloat % (Math.PI * 2).toFloat
  }
}
