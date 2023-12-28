fun main() {
    fun part1(input: List<String>): Int {
        val hands = input.map { Hand.parse(it, false) }

        val rankAndBids = hands
            .sorted()
            .mapIndexed { index, hand -> index + 1 to hand.bid }

        return rankAndBids.sumOf { it.first * it.second }
    }

    fun part2(input: List<String>): Int {
        val hands = input.map { Hand.parse(it, true) }

        val rankAndBids = hands
            .sorted()
            .mapIndexed { index, hand -> index + 1 to hand.bid }

        return rankAndBids.sumOf { it.first * it.second }
    }

    val input = readInput("input7")
    part1(input).println()
    part2(input).println()
}

data class Hand(val cards: String, val bid: Int, val hasJokers: Boolean) : Comparable<Hand> {
    companion object {
        private const val ORDER = "AKQJT98765432"
        private const val ORDER_WITH_JOKERS = "AKQT98765432J"
        fun parse(input: String, hasJokers: Boolean): Hand {
            val (cards, bid) = input.split(" ")
            return Hand(cards, bid.toInt(), hasJokers)
        }
    }

    override fun compareTo(other: Hand): Int {
        val handType = if (hasJokers) getTypeWithJoker() else getType()
        val otherHandType = if (hasJokers) other.getTypeWithJoker() else other.getType()

        if (handType != otherHandType) {
            return handType.strength.compareTo(otherHandType.strength)
        }

        val order = if (hasJokers) ORDER_WITH_JOKERS else ORDER

        cards.forEachIndexed { index, card ->
            val index1 = order.indexOf(card)
            val index2 = order.indexOf(other.cards[index])
            if (index1 < index2) return 1
            if (index1 > index2) return -1
        }
        return 0
    }

    private fun getType(): HandType {
        val groups = cards.groupingBy { it }.eachCount()
        return when {
            groups.size == 5 -> HandType.HIGH_CARD
            groups.size == 4 && groups.containsValue(2) -> HandType.ONE_PAIR
            groups.size == 3 && groups.containsValue(2) -> HandType.TWO_PAIR
            groups.size == 3 && groups.containsValue(3) -> HandType.THREE_OF_A_KIND
            groups.size == 2 && groups.containsValue(3) -> HandType.FULL_HOUSE
            groups.size == 2 && groups.containsValue(4) -> HandType.FOUR_OF_A_KIND
            groups.size == 1 -> HandType.FIVE_OF_A_KIND
            else -> error("Invalid hand!")
        }
    }

    private fun getTypeWithJoker(): HandType {
        var handType = getType()

        for (card in "AKQJT98765432") {
            val newCards = cards.replace('J', card)

            val newHandType = Hand(newCards, 0, false).getType()

            if (newHandType.strength > handType.strength) {
                handType = newHandType
            }
        }
        return handType
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
