package utils

import java.io.File
import kotlin.time.TimeSource

class Problem(day: Int, private val expectedTestResult: Int) {
    private val fileName = when {
        day < 10 -> "0$day"
        else -> "$day"
    }
    private val testPath = "./tests/$fileName"
    private val inputPath = "./inputs/$fileName"

    fun solve(solver: (List<String>) -> Int) {
        val testResult = solver(File(testPath).readLines())

        if (testResult != expectedTestResult) {
            println("test unsuccessful\nexpected: $expectedTestResult\nbut got: $testResult")
            return
        }

        val timeSource = TimeSource.Monotonic
        val start = timeSource.markNow()
        val result = solver(File(inputPath).readLines())
        val end = timeSource.markNow()

        println("time elapsed: ${end - start}")
        println("result: $result")
    }
}