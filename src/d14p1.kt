package d14p1

import utils.Problem

private typealias Pos = Pair<Int, Int>

private data class Robot(val pos: Pos, val velocity: Pos)

private fun parseRobot(row: String): Robot {
    val v = row.split(" ").map { it.split("=")[1].split(",").map { n -> n.toInt() } }
    return Robot(Pos(v[0][1], v[0][0]), Pos(v[1][1], v[1][0]))
}

private fun parseInput(input: List<String>): Triple<Int, Int, List<Robot>> {
    val dimensions = input[0].split('x').map { it.toInt() }
    val robots = input.drop(1).map { parseRobot(it) }
    return Triple(dimensions[0], dimensions[1], robots)
}

private fun moveRobots(height: Int, width: Int, robots: List<Robot>): List<Pos> {
    return robots.map {
        val (py, px) = it.pos
        val (vy, vx) = it.velocity
        return@map Pos(Math.floorMod(py + vy * 100, height), Math.floorMod(px + vx * 100, width))
    }
}

private fun getSafety(height: Int, width: Int, robots: List<Pos>): Long {
    var tl = 0L
    var tr = 0L
    var bl = 0L
    var br = 0L

    for (robot in robots) {
        val (y, x) = robot

        if (y < height / 2 && x < width / 2) {
            tl++
        } else if (y < height / 2 && x > width / 2) {
            tr++
        } else if (y > height / 2 && x < width / 2) {
            bl++
        } else if (y > height / 2 && x > width / 2) {
            br++
        }
    }
    return tl * tr * bl * br
}

private fun solver(input: List<String>): Long {
    val (height, width, robots) = parseInput(input)
    val movedRobots = moveRobots(height, width, robots)
    return getSafety(height, width, movedRobots)
}

fun main() {
    Problem(14, 12L).solve(::solver)
}

