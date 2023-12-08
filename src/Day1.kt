fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { it.calibrationValue() }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val input = readInput("input1")
    part1(input).println()
    part2(input).println()
}

fun String.calibrationValue(): Int {
    val firstDigit = first { it.isDigit() }
    val lastDigit = last { it.isDigit() }
    return "$firstDigit$lastDigit".toInt()
}
