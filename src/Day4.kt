import kotlin.math.pow

fun main() {
    fun part1(input: List<String>): Int {
        val scratchcards = input.map { Scratchcard.parse(it) }
        return scratchcards.sumOf { it.points() }
    }

    fun part2(input: List<String>): Int {
        val scratchcards = input.map { Scratchcard.parse(it) }

        val counts = MutableList(scratchcards.size) { 1 }

        scratchcards.forEachIndexed { index, scratchcard ->
            repeat(scratchcard.score) {
                counts[index + it + 1] += counts[index]
            }
        }
        return counts.sum()
    }

    val input = readInput("input4")
    part1(input).println()
    part2(input).println()
}

/**
 * A Scratchcard with the [score] of our numbers matching with the winning numbers.
 */
data class Scratchcard(val score: Int) {
    companion object {
        private val regex = """\s+""".toRegex()

        fun parse(input: String): Scratchcard {
            val list1 = input.substringAfter(":").substringBefore("|").trim()
            val winningNumbers = list1.split(regex).map { it.toInt() }

            val list2 = input.substringAfter("|").trim()
            val numbers = list2.split(regex).map { it.toInt() }
            val score = numbers.count { it in winningNumbers }
            return Scratchcard(score)
        }
    }

    fun points(): Int = 2.0.pow(score - 1).toInt()
}
