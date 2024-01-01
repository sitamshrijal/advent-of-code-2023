fun main() {
    fun part1(input: List<String>): Int {
        val readings = input.map { line ->
            line.split(" ").map { it.toInt() }
        }
        return readings.sumOf { it.extrapolate() }
    }

    fun part2(input: List<String>): Int {
        val readings = input.map { line ->
            line.split(" ").map { it.toInt() }
        }
        return readings.map { it.reversed() }.sumOf { it.extrapolate() }
    }

    val input = readInput("input9")
    part1(input).println()
    part2(input).println()
}

fun List<Int>.extrapolate(): Int = if (all { it == 0 }) {
    0
} else {
    val differences = windowed(2).map { it[1] - it[0] }
    differences.extrapolate() + last()
}
