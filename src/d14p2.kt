package d14p2

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

private fun printRobots(height: Int, width: Int, robots: List<Robot>, moves: Int) {
    // horizontal clusters appear at move 76, and vertical ones at move 2
    if ((moves - 76) % height == 0 && (moves - 2) % width == 0) {
        println(moves)
        var map = ""
        for (y in 0..<height) {
            var s = ""
            for (x in 0..<width) {
                s += if (robots.any { (p) -> p.first == y && p.second == x }) {
                    "#"
                } else {
                    "."
                }
            }
            map += s + "\n"
        }
        println(map)
        println()
        readLine()
    }
}

private fun moveRobots(height: Int, width: Int, robots: List<Robot>) {
    var newRobots = robots
    var moves = 1
    while (true) {
        newRobots = newRobots.map {
            val (py, px) = it.pos
            val (vy, vx) = it.velocity
            return@map Robot(Pos(Math.floorMod(py + vy, height), Math.floorMod(px + vx, width)), it.velocity)
        }
        printRobots(
            height, width, newRobots, moves++
        )
    }
}

private fun solver(input: List<String>) {
    val (height, width, robots) = parseInput(input)
    moveRobots(height, width, robots)
}

fun main() {
    Problem<Unit>(14, null).solve(::solver)
}

