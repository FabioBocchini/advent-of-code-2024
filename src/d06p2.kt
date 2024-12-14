package d06p2

import utils.Problem

private typealias Pos = Pair<Int, Int>

private val steps = mapOf('u' to Pair(-1, 0), 'r' to Pair(0, 1), 'd' to Pair(1, 0), 'l' to Pair(0, -1))

private fun turnRight(direction: Char): Char {
    return when (direction) {
        'u' -> 'r'
        'r' -> 'd'
        'd' -> 'l'
        else -> 'u'
    }
}

private fun parseMap(map: List<CharArray>): Pair<Pos, List<Pos>> {
    val obstacles = mutableListOf<Pos>()
    lateinit var agent: Pos

    for (y in map.indices) {
        for (x in map[0].indices) {
            when (map[y][x]) {
                '#' -> obstacles.add(Pair(y, x))
                '^' -> agent = Pos(y, x)
            }
        }
    }
    return Pair(agent, obstacles)
}

private fun getPath(
    height: Int, width: Int, startingAgent: Pos, obstacles: List<Pos>
): Set<Pos> {
    var direction = 'u'
    var agent = startingAgent
    val visited = mutableSetOf(agent)

    while (true) {
        val (dy, dx) = steps[direction]!!
        val (y, x) = agent
        val ny = y + dy
        val nx = x + dx

        if (ny < 0 || ny >= height || nx < 0 || nx >= width) {
            break
        }

        if (obstacles.contains(Pair(ny, nx))) {
            direction = turnRight(direction)
            continue
        }
        agent = Pos(ny, nx)

        if (!visited.contains(agent)) {
            visited.add(agent)
        }
    }

    return visited
}

private fun isLoop(
    height: Int, width: Int, startingAgent: Pos, obstacles: List<Pos>
): Boolean {
    var direction = 'u'
    var agent = startingAgent
    val visited = mutableSetOf(Pair(agent, direction))

    while (true) {
        val (dy, dx) = steps[direction]!!
        val (y, x) = agent
        val ny = y + dy
        val nx = x + dx

        if (ny < 0 || ny >= height || nx < 0 || nx >= width) {
            break
        }

        val newPos = Pos(ny, nx)

        if (obstacles.contains(newPos)) {
            direction = turnRight(direction)
        } else {
            agent = newPos
        }

        if (visited.contains(Pair(agent, direction))) {
            return true
        }
        visited.add(Pair(agent, direction))
    }

    return false
}

private fun solver(rows: List<String>): Int {
    val map = rows.map { it.toCharArray() }
    val (agent, obstacles) = parseMap(map)
    val height = map.size
    val width = map[0].size
    val path = getPath(height, width, agent, obstacles)
    return path.fold(0) { acc, pos ->
        val newObstacles = obstacles.toMutableList()
        newObstacles.add(pos)
        if (isLoop(height, width, agent, newObstacles)) {
            return@fold acc + 1
        }
        return@fold acc
    }
}

fun main() {
    Problem(6, 6).solve(::solver)
}
