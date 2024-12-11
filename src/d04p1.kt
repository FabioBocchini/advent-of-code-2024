import utils.Problem

private data class Step(val y: Int, val x: Int, val direction: String?, val pred: Char?)

private val steps: Map<String, Pair<Int, Int>> = mapOf(
    "tl" to Pair(-1, -1),
    "t" to Pair(-1, 0),
    "tr" to Pair(-1, 1),
    "l" to Pair(0, -1),
    "r" to Pair(0, 1),
    "bl" to Pair(1, -1),
    "b" to Pair(1, 0),
    "br" to Pair(1, 1)
)

private fun walk(map: List<CharArray>): Int {
    val width = map[0].size
    val height = map.size
    val stack = ArrayDeque<Step>()
    val visited = mutableListOf<Pair<Int, Int>>()
    var xmasFound = 0

    for (y in 0..<height) {
        for (x in 0..<width) {
            if (map[y][x] == 'X') {
                stack.add(Step(y, x, null, null))
            }
        }
    }

    while (stack.isNotEmpty()) {
        val step = stack.removeFirst()

        if (step.y < 0 || step.y >= height || step.x < 0 || step.x >= width) {
            continue
        }

        val currChar = map[step.y][step.x]
        if (currChar == 'X') {
            if (visited.contains(Pair(step.y, step.x))) {
                continue
            }
            visited.add(Pair(step.y, step.x))
            stack.addAll(steps.entries.map { (dir, mov) ->
                Step(step.y + mov.first, step.x + mov.second, dir, 'X')
            })
            continue
        }

        if (step.direction == null) {
            continue
        }

        if ((currChar == 'M' && step.pred == 'X') || currChar == 'A' && step.pred == 'M') {
            val (dy, dx) = steps[step.direction]!!
            stack.add(Step(step.y + dy, step.x + dx, step.direction, currChar))
            continue
        }

        if (currChar == 'S' && step.pred == 'A') {
            xmasFound++
        }
    }
    return xmasFound
}

private fun solver(rows: List<String>): Int {
    val map = rows.map { it.toCharArray() }
    return walk(map)
}

fun main() {
    Problem(4, 18).solve(::solver)
}
