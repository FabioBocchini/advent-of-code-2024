package d11p2

import utils.Problem

private fun parseInput(input: String): Map<Long, ULong> {
    val stoneMap = mutableMapOf<Long, ULong>()
    for (stone in input.split(" ")) {
        val stoneValue = stone.toLong()
        val prevStone = stoneMap[stoneValue] ?: 0UL
        stoneMap[stoneValue] = prevStone + 1UL
    }
    return stoneMap
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

private fun blink(stones: Map<Long, ULong>): Map<Long, ULong> {
    val stonesMap = mutableMapOf<Long, ULong>()

    for (stone in stones.entries) {
        for (newStone in transformStone(stone.key)) {
            val prevStoneValue = stonesMap[newStone] ?: 0UL
            stonesMap[newStone] = prevStoneValue + stone.value
        }
    }

    return stonesMap
}

private fun solver(input: String): ULong {
    var stones = parseInput(input)

    for (i in 0..<75) {
        stones = blink(stones)
    }
    return stones.entries.fold(0UL) { acc, s -> acc + s.value }
}

fun main() {
    Problem(11, 65601038650482UL).solveWithoutSplit(::solver)
}

