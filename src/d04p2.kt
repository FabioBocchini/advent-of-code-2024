package d04p2

import utils.Problem

private fun walk(map: List<CharArray>): Int {
    val width = map[0].size
    val height = map.size
    var xmasFound = 0

    for (y in 1..<height - 1) {
        for (x in 1..<width - 1) {
            if (map[y][x] != 'A') {
                continue
            }
            val tl = map[y - 1][x - 1]
            val tr = map[y - 1][x + 1]
            val bl = map[y + 1][x - 1]
            val br = map[y + 1][x + 1]

            if (((tl == 'M' && br == 'S') || (tl == 'S' && br == 'M')) &&
                ((tr == 'M' && bl == 'S') || (tr == 'S' && bl == 'M'))
            ) {
                xmasFound++
            }
        }
    }
    return xmasFound
}

private fun solver(rows: List<String>): Int {
    val map = rows.map { it.toCharArray() }
    return walk(map)
}

fun main() {
    Problem(4, 9).solve(::solver)
}
