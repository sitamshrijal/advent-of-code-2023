fun main() {
    fun part1(input: List<String>): Int {
        val whitespace = """\s+""".toRegex()
        val times = input[0].substringAfter(":").trim().split(whitespace).map { it.toInt() }
        val distances = input[1].substringAfter(":").trim().split(whitespace).map { it.toInt() }

        // Number of ways to beat the record in each race
        val counts = MutableList(times.size) { 0 }
        times.forEachIndexed { index, time ->
            val record = distances[index]

            for (t in 1..<time) {
                val travelled = (time - t) * t
                if (travelled > record) {
                    counts[index]++
                }
            }
        }
        return counts.reduce { acc, i -> acc * i }
    }

    fun part2(input: List<String>): Int {
        val whitespace = """\s+""".toRegex()
        val times = input[0].replace(" ", "").substringAfter(":").trim().split(whitespace)
            .map { it.toLong() }
        val distances = input[1].replace(" ", "").substringAfter(":").trim().split(whitespace)
            .map { it.toLong() }

        // Number of ways to beat the record in each race
        val counts = MutableList(times.size) { 0 }
        times.forEachIndexed { index, time ->
            val record = distances[index]

            for (t in 1..<time) {
                val travelled = (time - t) * t
                if (travelled > record) {
                    counts[index]++
                }
            }
        }
        return counts.reduce { acc, i -> acc * i }
    }

    val input = readInput("input6")
    part1(input).println()
    part2(input).println()
}
