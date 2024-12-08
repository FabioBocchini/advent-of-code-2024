import utils.Problem

private fun solver(rows: List<String>): Int {
    val fst = mutableListOf<Int>()
    val snd = mutableListOf<Int>()

    for (row in rows) {
        val splitRow = row.split(" ").filter(String::isNotEmpty)
        fst.add(splitRow[0].toInt())
        snd.add(splitRow[1].toInt())
    }

    fst.sort()
    snd.sort()

    var acc = 0

    for (i in rows.indices) {
        acc += if (fst[i] <= snd[i]) {
            snd[i] - fst[i]
        } else {
            fst[i] - snd[i]
        }
    }

    return acc
}

fun main() {
    Problem(1, 11).solve(::solver)
}