import utils.Problem

private fun isRowSafe(row: List<Int>): Boolean {
    var prev = row.first()
    var direction: Char? = null

    for (i in 1..<row.size) {
        val next = row[i]

        if (direction == null) {
            direction = when {
                prev < next -> 'g'
                else -> 'l'
            }
        }

        if (
            (direction == 'l' && prev > next && prev - next <= 3) ||
            (direction == 'g' && prev < next && next - prev <= 3)
        ) {
            prev = next
        } else {
            return false
        }
    }
    return true
}

private fun dampenAndSolve(row: List<Int>): Boolean {
    for (i in row.indices) {
        row.toMutableList().removeAt(i)
        if (isRowSafe(row.filterIndexed { index, _ -> index != i })) {
            return true
        }
    }
    return false
}

private fun solver(rows: List<String>): Int {
    val parsedRows = rows.map {
        it.split(" ").filter(String::isNotEmpty).map(String::toInt)
    }

    var acc = 0

    for (row in parsedRows) {
        if (isRowSafe(row) || dampenAndSolve(row)) {
            acc++
        }
    }
    return acc
}

fun main() {
    Problem(2, 4).solve(::solver)
}
