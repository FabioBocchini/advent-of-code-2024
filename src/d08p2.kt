package d08p2

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

private fun isInLine(pos: Pos, antennaGroup: List<Pos>): Boolean {
    for (i in antennaGroup.indices) {
        for (j in i + 1..<antennaGroup.size) {
            val (y, x) = pos
            val (iy, ix) = antennaGroup[i]
            val (jy, jx) = antennaGroup[j]

            if (y * ix - x * iy + iy * jx - ix * jy + x * jy - y * jx == 0) {
                return true
            }
        }
    }

    return false
}

private fun countAntinodes(height: Int, width: Int, antennas: Map<Char, List<Pos>>): Int {
    val antinodes: MutableSet<Pos> = mutableSetOf()

    for (y in 0..<height) {
        for (x in 0..<width) {
            for (antennaGroup in antennas.values) {
                val pos = Pos(y, x)
                if (pos in antinodes) {
                    continue
                }
                if (isInLine(pos, antennaGroup)) {
                    antinodes.add(pos)
                }
            }
        }
    }

    return antinodes.size
}

private fun solver(rows: List<String>): Int {
    val map = rows.map { it.toCharArray() }
    val antennas = getAntennas(map)
    val height = map.size
    val width = map[0].size
    return countAntinodes(height, width, antennas)
}

fun main() {
    Problem(8, 34).solve(::solver)
}
