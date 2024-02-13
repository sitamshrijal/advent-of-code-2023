import kotlin.io.path.Path
import kotlin.io.path.readText

fun main() {
    fun part1(input: String): Int {
        val steps = input.split(",")
        return steps.sumOf { it.hash() }
    }

    fun part2(input: String): Int {
        return input.length
    }

    val input = Path("src/input15.txt").readText()
    part1(input).println()
    part2(input).println()
}

fun String.hash(): Int {
    var value = 0
    for (char in this) {
        value += char.code
        value *= 17
        value %= 256
    }
    return value
}
