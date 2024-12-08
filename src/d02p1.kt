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

private fun solver(rows: List<String>): Int {
    return rows.map {
        it.split(" ").filter(String::isNotEmpty).map(String::toInt)
    }.count(::isRowSafe)
}

fun main() {
    Problem(2, 2).solve(::solver)
}
