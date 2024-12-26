package d11p1

import utils.Problem

private fun parseInput(input: String): List<Long> {
    return input.split(" ").map { it.toLong() }
}

private fun transformStone(stone: Long): List<Long> {
    if (stone == 0L) {
        return listOf(1L)
    }
    val stoneString = stone.toString()
    if (stoneString.length % 2 == 0) {
        return listOf(
            stoneString.slice(0..<stoneString.length / 2).toLong(),
            stoneString.slice(stoneString.length / 2..<stoneString.length).toLong()
        )
    }
    return listOf(stone * 2024)
}

private fun blink(stones: List<Long>): List<Long> {
    return stones.fold(mutableListOf()) { acc, stone ->
        acc.addAll(transformStone(stone))
        return@fold acc
    }
}

private fun solver(input: String): Long {
    var stones = parseInput(input)

    for (i in 0..<25) {
        stones = blink(stones)
    }
    return stones.size.toLong()
}

fun main() {
    Problem(11, 55312L).solveWithoutSplit(::solver)
}
