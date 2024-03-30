import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val galaxies = parseGalaxies(input)
        val emptyRows = galaxies.indices - galaxies.map { it.y }.toSet()
        val emptyColumns = galaxies.indices - galaxies.map { it.x }.toSet()

        val expandedGalaxies = galaxies.map { (x, y) ->
            val emptyColumnsBefore = (0..x).count { it in emptyColumns }
            val newX = x + if (emptyColumnsBefore == 0) 0 else emptyColumnsBefore

            val emptyRowsBefore = (0..y).count { it in emptyRows }
            val newY = y + if (emptyRowsBefore == 0) 0 else emptyRowsBefore
            Position2D(newX, newY)
        }

        val galaxyPairs = expandedGalaxies.cartesianPairs()

        return galaxyPairs.sumOf { (first, second) ->
            first.manhattanDistance(second)
        }
    }

    fun part2(input: List<String>): Long {
        val galaxies = parseGalaxies(input)
        val emptyRows = galaxies.indices - galaxies.map { it.y }.toSet()
        val emptyColumns = galaxies.indices - galaxies.map { it.x }.toSet()

        val expandedGalaxies = galaxies.map { (x, y) ->
            val emptyColumnsBefore = (0..x).count { it in emptyColumns }
            val newX = x + if (emptyColumnsBefore == 0) 0 else emptyColumnsBefore * 999_999

            val emptyRowsBefore = (0..y).count { it in emptyRows }
            val newY = y + if (emptyRowsBefore == 0) 0 else emptyRowsBefore * 999_999
            Position2D(newX, newY)
        }

        val galaxyPairs = expandedGalaxies.cartesianPairs()

        return galaxyPairs.sumOf { (first, second) ->
            first.manhattanDistance(second).toLong()
        }
    }

    val input = readInput("input11")
    part1(input).println()
    part2(input).println()
}

data class Position2D(val x: Int, val y: Int)

fun parseGalaxies(input: List<String>): List<Position2D> = buildList {
    input.forEachIndexed { y, row ->
        row.forEachIndexed { x, c ->
            if (c == '#') {
                add(Position2D(x, y))
            }
        }
    }
}

fun Position2D.manhattanDistance(other: Position2D): Int = abs(x - other.x) + abs(y - other.y)

fun <T> List<T>.cartesianPairs(): List<Pair<T, T>> {
    val pairs = mutableListOf<Pair<T, T>>()

    for (i in indices) {
        for (j in i + 1..lastIndex) {
            pairs += this[i] to this[j]
        }
    }
    return pairs
}
