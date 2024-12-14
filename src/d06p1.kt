package d06p1

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

private fun solver(rows: List<String>): Int {
    val map = rows.map { it.toCharArray() }
    val obstacles = mutableListOf<Pos>()
    var agent: Pos? = null

    for (y in map.indices) {
        for (x in map[0].indices) {
            when (map[y][x]) {
                '#' -> obstacles.add(Pair(y, x))
                '^' -> agent = Pos(y, x)
            }
        }
    }

    if (agent == null) {
        return -1
    }

    val height = map.size
    val width = map[0].size
    val visited = mutableSetOf(agent)
    var stepsTaken = 1
    var direction = 'u'

    while (true) {
        val (dy, dx) = steps[direction] ?: return -1
        val (y, x) = agent!!
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
            stepsTaken++
            visited.add(agent)
        }
    }

    return stepsTaken
}

fun main() {
    Problem(6, 41).solve(::solver)
}
