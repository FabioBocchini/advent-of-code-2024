package d13p1

import utils.Problem

private data class Machine(val ax: Int, val ay: Int, val bx: Int, val by: Int, val px: Int, val py: Int)

private fun parseButton(row: String): Pair<Int, Int> {
    val values = row.split(": ")[1].split(", ").map {
        it.trim().split("+")[1].toInt()
    }
    return Pair(values[0], values[1])
}

private fun parsePrice(row: String): Pair<Int, Int> {
    val values = row.split(": ")[1].split(", ").map {
        it.trim().split("=")[1].toInt()
    }
    return Pair(values[0], values[1])
}

private fun parseMachines(input: String): List<Machine> {
    return input.split("\n\n").map { m ->
        val rows = m.split("\n")
        val (ax, ay) = parseButton(rows[0])
        val (bx, by) = parseButton(rows[1])
        val (px, py) = parsePrice(rows[2])
        return@map Machine(ax, ay, bx, by, px, py)
    }
}

private fun playMachine(machine: Machine): Int {
    val (ax, ay, bx, by, px, py) = machine
    val bPresses = (py * ax - ay * px) / (ax * by - ay * bx)
    val aPresses = (px - bx * bPresses) / ax
    if (ax * aPresses + bx * bPresses == px && ay * aPresses + by * bPresses == py && aPresses <= 100 && bPresses <= 100) {
        return bPresses + aPresses * 3
    }
    return 0
}

private fun solver(input: String): Int {
    return parseMachines(input).sumOf(::playMachine)
}

fun main() {
    Problem(13, 480).solveWithoutSplit(::solver)
}

