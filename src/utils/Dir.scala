package utils

import java.lang.Math

import battlecode.common.Direction

object Dir {
  var E = new Direction(0.0f)
  var N = new Direction(Math.PI.toFloat/2.0f)
  var W = new Direction(Math.PI.toFloat)
  var S = new Direction(3*Math.PI.toFloat/2.0f)
  var NE = new Direction(Math.PI.toFloat/4.0f)
  var NW = new Direction(3*Math.PI.toFloat/4.0f)
  var SE = new Direction(7*Math.PI.toFloat/2.0f)
  var SW = new Direction(5*Math.PI.toFloat/2.0f)

  var dirs = Vector(N, E, S, W, NE, NW, SE, SW)

  // Average of a list of directions
  def avgDir(dirs : Array[Float]) : Float  = {
    return dirs(0)
//    return Math.atan2(dirs.map(d => Math.sin(d)).sum / dirs.length,
//                      dirs.map(d => Math.cos(d)).sum / dirs.length).toFloat % (Math.PI * 2).toFloat
  }
}
