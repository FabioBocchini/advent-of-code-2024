package d07p2

import utils.Problem
import java.util.*

private typealias Equation = Pair<Long, List<Long>>

private fun parseRow(row: String): Equation {
    val splitRow = row.split(':')
    val result = splitRow[0].toLong()
    val numbers = splitRow[1].trim().split(' ').map { it.toLong() }
    return Pair(result, numbers)
}

private fun dispositionWithRepetition(alphabet: List<Char>, length: Int): List<List<Char>> {
    val result = mutableListOf<List<Char>>()
    val stack = Stack<List<Char>>()
    stack.push(listOf())

    while (stack.isNotEmpty()) {
        val popped = stack.pop()
        val toAdd = alphabet.map {
            val newStackItem = popped.toMutableList()
            newStackItem.add(it)
            return@map newStackItem
        }
        if (popped.size == length - 1) {
            result.addAll(toAdd)
        } else {
            toAdd.forEach { stack.push(it) }
        }
    }

    return result
}

private fun testEquation(equation: Equation): Boolean {
    val (result, numbers) = equation
    val dispositions = dispositionWithRepetition(listOf('+', '*', '|'), numbers.size - 1)

    return dispositions.any { operations ->
        result == numbers.reduceIndexed { index, acc, i ->
            when (operations[index - 1]) {
                '+' -> acc + i
                '*' -> acc * i
                '|' -> (acc.toString() + i.toString()).toLong()
                else -> acc
            }
        }
    }
}

private fun solver(rows: List<String>): Long {
    return rows
        .map(::parseRow)
        .filter(::testEquation)
        .fold(0.toLong()) { acc, i -> acc + i.first }
}

fun main() {
    Problem(7, 11387L).solve(::solver)
}
