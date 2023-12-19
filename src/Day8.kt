fun main() {
    fun part1(input: List<String>): Int {
        val instructions = input[0]
        val network = input.drop(2).map { Node.parse(it) }

        val start = network.find { it.name == "AAA" }
        val end = network.find { it.name == "ZZZ" }
        var current = start
        var count = 0

        while (current != end) {
            for (instruction in instructions) {
                current = when (instruction) {
                    'L' -> network.find { it.name == current?.left }
                    'R' -> network.find { it.name == current?.right }
                    else -> error("Invalid instruction!")
                }
                count++
            }
        }
        return count
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val input = readInput("input8")
    part1(input).println()
    part2(input).println()
}

data class Node(val name: String, val left: String, val right: String) {
    companion object {
        private val regex = """\w+""".toRegex()

        fun parse(input: String): Node {
            val matches = regex.findAll(input).map { it.value }.toList()
            return Node(matches[0], matches[1], matches[2])
        }
    }
}
