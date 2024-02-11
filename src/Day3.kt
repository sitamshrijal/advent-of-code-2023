fun main() {
    fun part1(input: List<String>): Int {
        val regex = """\d+""".toRegex()

        val parts = buildList {
            input.forEachIndexed { i, row ->
                addAll(regex.findAll(row).map { Part(it.value.toInt(), it.range, i) })
            }
        }
        val validParts = parts.filter { part ->
            val adjacentPositions = part.adjacentPositions()

            val adjacentChars = adjacentPositions.mapNotNull { (i, j) ->
                val result = runCatching { input[i][j] }
                result.getOrNull()
            }
            adjacentChars.any { it != '.' }
        }
        return validParts.sumOf { it.number }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val input = readInput("input3")
    part1(input).println()
    part2(input).println()
}

data class Part(val number: Int, val range: IntRange, val row: Int) {
    fun adjacentPositions(): List<Pair<Int, Int>> {
        val i = row
        val j1 = range.first
        val j2 = range.last
        return buildList {
            add(i to j1 - 1)
            add(i to j2 + 1)

            for (j in j1 - 1..j2 + 1) {
                add(i - 1 to j)
                add(i + 1 to j)
            }
        }
    }
}
