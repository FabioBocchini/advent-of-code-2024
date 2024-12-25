package d09p2

import utils.Problem
import java.util.*
import kotlin.collections.ArrayDeque

private data class File(val id: Long, val index: Long, val length: Long)
private data class FreeSpace(val index: Long, val length: Long)

private fun parseDisk(disk: CharArray): Pair<ArrayDeque<File>, TreeMap<Long, FreeSpace>> {
    val files = ArrayDeque<File>()
    val freeSpaces = TreeMap<Long, FreeSpace>()
    var isFile = true
    var diskIndex = 0L

    for (i in disk.indices) {
        val length = disk[i].digitToInt().toLong()
        if (isFile) {
            files.add(File(i / 2L, diskIndex, length))
        } else if (length > 0) {
            freeSpaces[diskIndex] = FreeSpace(diskIndex, length)
        }
        isFile = !isFile
        diskIndex += length
    }
    return Pair(files, freeSpaces)
}

private fun getPartialChecksum(startingId: Long, startingIndex: Long, fileLength: Long): Long {
    return startingId * fileLength * (2 * startingIndex + fileLength - 1) / 2
}

private fun getChecksum(files: ArrayDeque<File>, freeSpaces: TreeMap<Long, FreeSpace>): Long {
    var checksum = 0L

    while (files.isNotEmpty()) {
        val file = files.removeLast()
        val freeSpace = freeSpaces.headMap(file.index).values.firstOrNull { it.length >= file.length }
        if (freeSpace == null || freeSpace.index > file.index) {
            checksum += getPartialChecksum(file.id, file.index, file.length)
            continue
        }
        freeSpaces.remove(freeSpace.index)
        checksum += getPartialChecksum(file.id, freeSpace.index, file.length)
        if (freeSpace.length > file.length) {
            freeSpaces[freeSpace.index + file.length] =
                FreeSpace(freeSpace.index + file.length, freeSpace.length - file.length)
        }
    }
    return checksum
}

private fun solver(file: String): Long {
    val (files, freeSpaces) = parseDisk(file.toCharArray())
    return getChecksum(files, freeSpaces)
}

fun main() {
    Problem(9, 2858L).solveWithoutSplit(::solver)
}
