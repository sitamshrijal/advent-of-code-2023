fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { it.calibrationValue() }
    }

    fun part2(input: List<String>): Int {
        val map = mapOf(
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9
        )

        val mapped = input.map { line ->
            buildString {
                line.forEachIndexed { index, c ->
                    if (c.isDigit()) {
                        append(c)
                    } else {
                        val substring = line.substring(index)

                        map.forEach { (key, value) ->
                            if (substring.startsWith(key)) {
                                append(value)
                            }
                        }
                    }
                }
            }
        }
        return mapped.sumOf { it.calibrationValue() }
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
