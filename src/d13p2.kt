package d13p2

import utils.Problem
import java.math.BigInteger

private data class Machine(
    val ax: BigInteger,
    val ay: BigInteger,
    val bx: BigInteger,
    val by: BigInteger,
    val px: BigInteger,
    val py: BigInteger
)

private fun parseButton(row: String): Pair<BigInteger, BigInteger> {
    val values = row.split(": ")[1].split(", ").map {
        it.trim().split("+")[1].toBigInteger()
    }
    return Pair(values[0], values[1])
}

private fun parsePrice(row: String): Pair<BigInteger, BigInteger> {
    val values = row.split(": ")[1].split(", ").map {
        it.trim().split("=")[1].toBigInteger() + 10000000000000.toBigInteger()
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

private fun playMachine(machine: Machine): BigInteger {
    val (ax, ay, bx, by, px, py) = machine
    val bPresses = (py * ax - ay * px) / (ax * by - ay * bx)
    val aPresses = (px - bx * bPresses) / ax
    if (ax * aPresses + bx * bPresses == px && ay * aPresses + by * bPresses == py) {
        return bPresses + aPresses * 3.toBigInteger()
    }
    return 0.toBigInteger()
}

private fun solver(input: String): BigInteger {
    return parseMachines(input).sumOf(::playMachine)
}

fun main() {
    Problem(13, 875318608908.toBigInteger()).solveWithoutSplit(::solver)
}

