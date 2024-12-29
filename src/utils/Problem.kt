package utils

import java.io.File
import kotlin.time.TimeSource

class Problem<N>(day: Int, private val expectedTestResult: N?) {
    private val fileName = when {
        day < 10 -> "0$day"
        else -> "$day"
    }
    private val testPath = "./tests/$fileName"
    private val inputPath = "./inputs/$fileName"

    private fun <T> solve(solver: (T) -> N, testInput: T, mainInput: T) {
        if (expectedTestResult != null) {
            val testResult = solver(testInput)

            if (testResult != expectedTestResult) {
                println("test unsuccessful\nexpected: $expectedTestResult\nbut got: $testResult")
                return
            }
        }
        val timeSource = TimeSource.Monotonic
        val start = timeSource.markNow()
        val result = solver(mainInput)
        val end = timeSource.markNow()

        println("time elapsed: ${end - start}")
        println("result: $result")
    }

    fun solve(solver: (List<String>) -> N) {
        val testInput = File(testPath).readLines()
        val mainInput = File(inputPath).readLines()
        solve(solver, testInput, mainInput)
    }

    fun solveWithoutSplit(solver: (String) -> N) {
        val testInput = File(testPath).readText()
        val mainInput = File(inputPath).readText()
        solve(solver, testInput, mainInput)
    }
}