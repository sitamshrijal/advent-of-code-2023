fun main() {
    fun part1(input: List<String>): Int {
        val scratchcards = input.map { Scratchcard.parse(it) }
        return scratchcards.sumOf { it.points() }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val input = readInput("input4")
    part1(input).println()
    part2(input).println()
}

data class Scratchcard(val winningNumbers: List<Int>, val numbers: List<Int>) {
    companion object {
        private val regex = """\s+""".toRegex()

        fun parse(input: String): Scratchcard {
            val list1 = input.substringAfter(":").substringBefore("|").trim()
            val winningNumbers = list1.split(regex).map { it.toInt() }

            val list2 = input.substringAfter("|").trim()
            val numbers = list2.split(regex).map { it.toInt() }
            return Scratchcard(winningNumbers, numbers)
        }
    }

    fun points(): Int {
        var points = 0
        for (number in numbers) {
            if (number in winningNumbers) {
                if (points == 0) {
                    points += 1
                } else {
                    points *= 2
                }
            }
        }
        return points
    }
}
