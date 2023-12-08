fun main() {
    fun part1(input: List<String>): Int {
        val games = input.map { Game.parse(it) }

        return games.filter {
            it.red <= 12 && it.green <= 13 && it.blue <= 14
        }.sumOf { it.id }
    }

    fun part2(input: List<String>): Int {
        val games = input.map { Game.parse(it) }

        return games.sumOf { it.power() }
    }

    val input = readInput("input2")
    part1(input).println()
    part2(input).println()
}

data class Game(val id: Int, val red: Int, val green: Int, val blue: Int) {
    companion object {
        fun parse(input: String): Game {
            val id = input.substringAfter(" ").substringBefore(":").toInt()

            val colors = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)

            val splits = input.substringAfter(": ").split("; ")

            for (split in splits) {
                val dice = split.split(", ")

                for (die in dice) {
                    val (value, color) = die.split(" ")
                    colors[color] = maxOf(value.toInt(), colors[color] ?: 0)
                }
            }
            return Game(id, colors["red"] ?: 0, colors["green"] ?: 0, colors["blue"] ?: 0)
        }
    }

    fun power(): Int = red * green * blue
}
