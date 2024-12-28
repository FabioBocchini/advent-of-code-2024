package d12p2

import utils.Problem

private typealias Pos = Pair<Int, Int>

private val steps: List<Pos> = listOf(
    Pos(-1, 0),
    Pos(0, -1),
    Pos(0, 1),
    Pos(1, 0),
)

private val diagonals: List<Pos> = listOf(
    Pos(-1, -1),
    Pos(-1, 1),
    Pos(1, -1),
    Pos(1, 1),
)

private fun getNeighbours(grid: List<String>, pos: Pos, plant: Char): List<Pos> {
    val neighbours = mutableListOf<Pos>()
    val (y, x) = pos
    for ((dy, dx) in steps) {
        try {
            if (grid[y + dy][x + dx] == plant) {
                neighbours.add(Pos(y + dy, x + dx))
            }
        } catch (e: Exception) {
            continue
        }
    }
    return neighbours
}

private fun getGridValue(grid: List<String>, y: Int, x: Int): Char {
    return try {
        grid[y][x]
    } catch (e: Exception) {
        '.'
    }
}

private fun countCorners(grid: List<String>, pos: Pos): Int {
    val (y, x) = pos
    val plant = grid[y][x]
    var corners = 0

    for ((dy, dx) in diagonals) {
        val rowN = getGridValue(grid, y, x + dx)
        val colN = getGridValue(grid, y + dy, x)
        val diaN = getGridValue(grid, y + dy, x + dx)

        if (rowN != plant && colN != plant) {
            corners++
        }

        if (rowN == plant && colN == plant && diaN != plant) {
            corners++
        }
    }
    return corners
}

private fun expandArea(
    grid: List<String>, toVisit: ArrayDeque<Pos>, area: MutableList<Pos> = mutableListOf(), corners: Int = 0
): Pair<List<Pos>, Int> {
    if (toVisit.isEmpty()) {
        return Pair(area, corners)
    }
    val pos = toVisit.removeFirst()
    if (pos in area) {
        return expandArea(grid, toVisit, area, corners)
    }
    area.add(pos)
    val (y, x) = pos
    val neighbours = getNeighbours(grid, pos, grid[y][x])
    toVisit.addAll(neighbours)
    val newCorners = corners + countCorners(grid, pos)
    return expandArea(grid, toVisit, area, newCorners)
}

private fun getPrice(grid: List<String>): Long {
    val visited: MutableSet<Pos> = mutableSetOf()
    var price = 0L

    for (y in grid.indices) {
        for (x in grid[0].indices) {
            if (Pos(y, x) in visited) {
                continue
            }
            val (area, sides) = expandArea(grid, ArrayDeque(listOf(Pos(y, x))))
            price += area.size * sides
            visited.addAll(area)
        }
    }
    return price
}

private fun solver(input: List<String>): Long {
    return getPrice(input)
}

fun main() {
    Problem(12, 1206L).solve(::solver)
}

