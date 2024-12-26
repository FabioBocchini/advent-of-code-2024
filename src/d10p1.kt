package d10p1

import utils.Problem

private typealias Grid = MutableList<List<Int>>
private typealias Pos = Pair<Int, Int>

private val steps: Map<String, Pos> = mapOf(
    "u" to Pos(-1, 0),
    "l" to Pos(0, -1),
    "r" to Pos(0, 1),
    "d" to Pos(1, 0),
)

private fun parseGrid(input: List<String>): Pair<Grid, List<Pos>> {
    val grid: Grid = mutableListOf()
    val startingPos: MutableList<Pos> = mutableListOf()

    for (y in input.indices) {
        val row = mutableListOf<Int>()
        for (x in input[0].indices) {
            val value = input[y][x].digitToInt()
            row.add(value)
            if (value == 0) {
                startingPos.add(Pos(y, x))
            }
        }
        grid.add(row)
    }
    return Pair(grid, startingPos)
}

private fun countScore(grid: Grid, toVisit: ArrayDeque<Pos>, endsFound: MutableSet<Pos> = mutableSetOf()): Long {
    if (toVisit.isEmpty()) {
        return endsFound.size.toLong()
    }
    val (y, x) = toVisit.removeFirst()
    val value = grid[y][x]
    if (value == 9) {
        endsFound.add(Pos(y, x))
        return countScore(grid, toVisit, endsFound)
    }

    for ((dy, dx) in steps.values) {
        try {
            if (grid[y + dy][x + dx] == value + 1) {
                toVisit.addFirst(Pos(y + dy, x + dx))
            }
        } catch (e: Exception) {
            continue
        }
    }

    return countScore(grid, toVisit, endsFound)
}


private fun solver(input: List<String>): Long {
    val (grid, startingPositions) = parseGrid(input)
    return startingPositions.sumOf { countScore(grid, ArrayDeque(listOf(it))) }
}

fun main() {
    Problem(10, 36L).solve(::solver)
}
