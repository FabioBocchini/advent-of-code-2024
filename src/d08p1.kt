package d08p1

import utils.Problem

typealias Pos = Pair<Int, Int>

private fun getAntennas(map: List<CharArray>): Map<Char, List<Pos>> {
    val antennas = mutableMapOf<Char, MutableList<Pos>>()
    for (y in map.indices) {
        for (x in map[0].indices) {
            val char = map[y][x]
            if (char == '.') {
                continue
            }

            val prevAntennas = antennas.getOrDefault(char, mutableListOf())
            prevAntennas.add(Pos(y, x))
            antennas[char] = prevAntennas
        }
    }
    return antennas
}

private fun getAntinoes(a: Pos, b: Pos): List<Pos> {
    val (ay, ax) = a
    val (by, bx) = b

    val dy = ay - by
    val dx = ax - bx

    return listOf(Pair(ay + dy, ax + dx), Pair(by - dy , bx - dx))
}

private fun getAllAntinodes(antennas: List<Pos>): List<Pos> {
    val antinodes = mutableSetOf<Pos>()

    for (i in antennas.indices) {
        for (j in i + 1..<antennas.size) {
            antinodes.addAll(getAntinoes(antennas[i], antennas[j]))
        }
    }

    return antinodes.toList()

}

private fun solver(rows: List<String>): Int {
    val map = rows.map { it.toCharArray() }
    val height = map.size
    val width = map[0].size
    return getAntennas(map)
        .values
        .map(::getAllAntinodes)
        .flatten()
        .toSet()
        .count { (y, x) -> y in 0..<height && x in 0..<width }
}

fun main() {
    Problem(8, 14).solve(::solver)
}
