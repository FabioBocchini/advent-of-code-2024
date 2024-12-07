private fun solver(rows: List<String>): Int {
    val fst = mutableListOf<Int>()
    val snd = mutableListOf<Int>()

    for (row in rows) {
        val splitRow = row.split(" ").filter(String::isNotEmpty)
        fst.add(splitRow[0].toInt())
        snd.add(splitRow[1].toInt())
    }

    return fst.fold(0) { acc, i -> acc + i * snd.count { it == i } }
}

fun main() {
    Problem(1, 31).solve(::solver)
}