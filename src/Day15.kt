import kotlin.io.path.Path
import kotlin.io.path.readText

fun main() {
    fun part1(input: String): Int {
        val steps = input.split(",")
        return steps.sumOf { it.hash() }
    }

    fun part2(input: String): Int {
        val steps = input.split(",")

        val boxes = List<MutableMap<String, Int>>(256) { mutableMapOf() }

        for (step in steps) {
            if (step.endsWith("-")) {
                val label = step.removeSuffix("-")
                val hash = label.hash()
                boxes[hash] -= label
            } else {
                val label = step.substringBefore("=")
                val hash = label.hash()
                val focalLength = step.substringAfter("=").toInt()
                boxes[hash][label] = focalLength
            }
        }
        var sum = 0
        boxes.forEachIndexed { i, contents ->
            contents.onEachIndexed { j, lens ->
                sum += (i + 1) * (j + 1) * lens.value
            }
        }
        return sum
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
