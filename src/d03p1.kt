package d03p1

import utils.FiniteStateMachine
import utils.Problem
import utils.Rule

private fun isNumber(char: Char): Boolean {
    return char in '0'..'9'
}

/*
*  STATES
*  0 = initial
*  1 = first number length 1
*  2 = first number length 2
*  3 = first number length 3
*  4 = comma
*  5 = second number length 1
*  6 = second number length 2
*  7 = second number length 3
* */

private val behavior = listOf(
    Rule(stateFrom = 0, condition = ::isNumber, stateTo = 1, output = { it.toString() }),
    Rule(stateFrom = 1, condition = ::isNumber, stateTo = 2, output = { it.toString() }),
    Rule(stateFrom = 1, condition = { it == ',' }, stateTo = 4, output = { it.toString() }),
    Rule(stateFrom = 2, condition = ::isNumber, stateTo = 3, output = { it.toString() }),
    Rule(stateFrom = 2, condition = { it == ',' }, stateTo = 4, output = { it.toString() }),
    Rule(stateFrom = 3, condition = { it == ',' }, stateTo = 4, output = { it.toString() }),
    Rule(stateFrom = 4, condition = ::isNumber, stateTo = 5, output = { it.toString() }),
    Rule(stateFrom = 5, condition = ::isNumber, stateTo = 6, output = { it.toString() }),
    Rule(stateFrom = 5, condition = { it == ')' }, stateTo = 7, output = { "ACCEPT" }),
    Rule(stateFrom = 6, condition = ::isNumber, stateTo = 7, output = { it.toString() }),
    Rule(stateFrom = 6, condition = { it == ')' }, stateTo = 7, output = { "ACCEPT" }),
    Rule(stateFrom = 7, condition = { it == ')' }, stateTo = 7, output = { "ACCEPT" }),
)

private fun solveRow(row: String, fsm: FiniteStateMachine<Int>): Int {
    return row.split("mul(").fold(0) { acc, input ->
        val res = fsm.execute(input) ?: return@fold acc
        val values = res.split(',')
        return@fold acc + values[0].toInt() * values[1].toInt()
    }
}

private fun solver(rows: List<String>): Int {
    val fsm = FiniteStateMachine(behavior, 0)
    return rows.fold(0) { acc, row -> acc + solveRow(row, fsm) }
}

fun main() {
    Problem(3, 161).solve(::solver)
}
