import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val galaxies = buildList {
            input.forEachIndexed { y, row ->
                row.forEachIndexed { x, c ->
                    if (c == '#') {
                        add(Position2D(x, y))
                    }
                }
            }
        }
        val emptyRows = galaxies.indices - galaxies.map { it.y }.toSet()
        val emptyColumns = galaxies.indices - galaxies.map { it.x }.toSet()

        val expandedGalaxies = mutableListOf<Position2D>()
        for (galaxy in galaxies) {
            val emptyColumnsBefore = (0..galaxy.x).count { it in emptyColumns }
            val newX = galaxy.x + if (emptyColumnsBefore == 0) 0 else emptyColumnsBefore

            val emptyRowsBefore = (0..galaxy.y).count { it in emptyRows }
            val newY = galaxy.y + if (emptyRowsBefore == 0) 0 else emptyRowsBefore
            expandedGalaxies += Position2D(newX, newY)
        }

        val galaxyPairs = expandedGalaxies.cartesianPairs()

        return galaxyPairs.sumOf { (first, second) ->
            first.manhattanDistance(second)
        }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val input = readInput("input11")
    part1(input).println()
    part2(input).println()
}

data class Position2D(val x: Int, val y: Int)

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
