package d12p1

import utils.Problem

private typealias Pos = Pair<Int, Int>

private val steps: List<Pos> = listOf(
    Pos(-1, 0),
    Pos(0, -1),
    Pos(0, 1),
    Pos(1, 0),
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


private fun expandArea(
    grid: List<String>, toVisit: ArrayDeque<Pos>, area: MutableList<Pos> = mutableListOf(), perimeter: Long = 0L
): Pair<List<Pos>, Long> {
    if (toVisit.isEmpty()) {
        return Pair(area, perimeter)
    }
    val pos = toVisit.removeFirst()
    if (pos in area) {
        return expandArea(grid, toVisit, area, perimeter)
    }
    area.add(pos)
    val (y, x) = pos
    val neighbours = getNeighbours(grid, pos, grid[y][x])
    toVisit.addAll(neighbours)
    return expandArea(grid, toVisit, area, perimeter + 4 - neighbours.size)
}

private fun getPrice(grid: List<String>): Long {
    val visited: MutableSet<Pos> = mutableSetOf()
    var price = 0L

    for (y in grid.indices) {
        for (x in grid[0].indices) {
            if (Pos(y, x) in visited) {
                continue
            }
            val (area, perimeter) = expandArea(grid, ArrayDeque(listOf(Pos(y, x))))
            price += area.size * perimeter
            visited.addAll(area)
        }
    }
    return price
}

private fun solver(input: List<String>): Long {
    return getPrice(input)
}

fun main() {
    Problem(12, 1930L).solve(::solver)
}

