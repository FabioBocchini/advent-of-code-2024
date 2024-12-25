package d09p1

import utils.Problem

private fun parseDisk(input: CharArray): List<Int?> {
    val disk: MutableList<Int?> = mutableListOf()
    var isFile = true
    var id = 0

    for (i in input) {
        val length = i.digitToInt()
        if (isFile) {
            disk.addAll(List(length) { id })
            id ++
        } else {
            disk.addAll(List(length) { null })
        }
        isFile = !isFile
    }

    return disk
}

private fun compactDisk(disk: List<Int?>): List<Int> {
    val compactedDisk: MutableList<Int> = mutableListOf()
    var i = 0
    var j = disk.size - 1

    while (i <= j) {
        if (disk[i] !== null) {
            compactedDisk.add(disk[i]!!)
            i++
        } else if (disk[j] !== null) {
            compactedDisk.add(disk[j]!!)
            j--
            i++
        } else {
            j--
        }
    }
    return compactedDisk
}

private fun calculateChecksum(disk: List<Int>): Long {
    return disk.foldIndexed(0) { index, acc, id -> acc + id * index }
}

private fun solver(file: String): Long {
    val expandedDisk = parseDisk(file.toCharArray())
    val compactedDisk = compactDisk(expandedDisk)
    return calculateChecksum(compactedDisk)
}

fun main() {
    Problem(9, 1928L).solveWithoutSplit(::solver)
}
