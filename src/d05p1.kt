package d05p1

import utils.Problem

private typealias Rules = MutableMap<String, MutableList<String>>

private fun foldRules(acc: Rules, rule: List<String>): Rules {
    val mapEntry = acc[rule[0]] ?: mutableListOf()
    mapEntry.add(rule[1])
    acc[rule[0]] = mapEntry
    return acc
}

private fun isUpdateValid(update: List<String>, rules: Rules): Boolean {
    val visited = mutableListOf<String>()

    for (i in update) {
        if (rules[i]?.any { j -> visited.contains(j) } == true) {
            return false
        }
        visited.add(i)
    }
    return true
}

private fun getUpdateMidPoint(update: List<String>): Int {
    val a = update[(update.size.toFloat() / 2).toInt()].toInt()
    return a
}

private fun solver(file: String): Int {
    val fileParts = file.split("\n\n")
    val rules = fileParts[0].split("\n").map { it.split("|") }.fold(mutableMapOf(), ::foldRules)
    val updates = fileParts[1].split("\n").map { it.split(",") }

    return updates.filter { update -> isUpdateValid(update, rules) }
        .fold(0) { acc, update -> acc + getUpdateMidPoint(update) }
}

fun main() {
    Problem(5, 143).solveWithoutSplit(::solver)
}
