fun main() {
    fun part1(input: List<String>): Int {
        val hands = input.map { Hand.parse(it) }

        val rankAndBids = hands
            .sorted()
            .mapIndexed { index, hand -> index + 1 to hand.bid }

        return rankAndBids.sumOf { it.first * it.second }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val input = readInput("input7")
    part1(input).println()
    part2(input).println()
}

data class Hand(val cards: String, val bid: Int) : Comparable<Hand> {
    companion object {
        private const val ORDER = "AKQJT98765432"
        fun parse(input: String): Hand {
            val (cards, bid) = input.split(" ")
            return Hand(cards, bid.toInt())
        }
    }

    override fun compareTo(other: Hand): Int {
        val handType = getType()
        val otherHandType = other.getType()

        if (handType != otherHandType) {
            return handType.strength.compareTo(otherHandType.strength)
        }

        cards.forEachIndexed { index, card ->
            val index1 = ORDER.indexOf(card)
            val index2 = ORDER.indexOf(other.cards[index])
            if (index1 < index2) return 1
            if (index1 > index2) return -1
        }
        return 0
    }

    private fun getType(): HandType {
        val groups = cards.groupingBy { it }.eachCount()
        val size = groups.size
        return when {
            size == 5 -> HandType.HIGH_CARD
            size == 4 && groups.containsValue(2) -> HandType.ONE_PAIR
            size == 3 && groups.containsValue(2) -> HandType.TWO_PAIR
            size == 3 && groups.containsValue(3) -> HandType.THREE_OF_A_KIND
            size == 2 && groups.containsValue(3) -> HandType.FULL_HOUSE
            size == 2 && groups.containsValue(4) -> HandType.FOUR_OF_A_KIND
            size == 1 -> HandType.FIVE_OF_A_KIND
            else -> error("Invalid hand!")
        }
    }
}

enum class HandType(val strength: Int) {
    FIVE_OF_A_KIND(7),
    FOUR_OF_A_KIND(6),
    FULL_HOUSE(5),
    THREE_OF_A_KIND(4),
    TWO_PAIR(3),
    ONE_PAIR(2),
    HIGH_CARD(1)
}
