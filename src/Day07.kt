import kotlin.math.max

fun main() {
    fun handRank(hand: String): Int {
        val sortedHand = hand.toCharArray().sorted().joinToString("");
        if (sortedHand[0] == sortedHand[4]) return 7

        if (sortedHand[0] == sortedHand[3]) return 6
        if (sortedHand[1] == sortedHand[4]) return 6

        if (sortedHand[0] == sortedHand[1] &&
            sortedHand[2] == sortedHand[4]) return 5
        if (sortedHand[0] == sortedHand[2] &&
            sortedHand[3] == sortedHand[4]) return 5

        for (i in 0..2) {
            if (sortedHand[i] == sortedHand[i+1] &&
                sortedHand[i] == sortedHand[i+2]) return 4
        }

        var pairs = 0
        for (i in 0..3) {
            if (sortedHand[i] == sortedHand[i+1]) pairs++
        }
        if (pairs == 2) return 3
        if (pairs == 1) return 2

        return 1
    }

    fun lexicographicalCards(hand: String) : String {
        var newHand = ""
        for (c in hand) {
            if (c == 'A') newHand += "Z"
            else if (c == 'K') newHand += "Y"
            else if (c == 'Q') newHand += "X"
            else if (c == 'J') newHand += "W"
            else newHand += c
        }
        return newHand
    }

    fun compareHands(): Comparator<Pair<String, Int>> {
        return Comparator { a, b ->
            val rankA = handRank(a.first)
            val rankB = handRank(b.first)

            when {
                rankA > rankB -> -1
                rankB > rankA -> 1
                lexicographicalCards(a.first) > lexicographicalCards(b.first) -> -1
                else -> 1
            }
        }
    }

    fun part1(input: List<String>): Int {
        val list = mutableListOf<Pair<String, Int>>()
        for (line in input) {
            val words = line.split(" ")
            list.add(Pair(words[0], words[1].toInt()))
        }
        list.sortWith(compareHands())
        list.reverse()
        var ans = 0
        for (i in list.indices) {
            ans += list[i].second * (i+1)
        }
        return ans
    }

    fun handRank2(hand: String): Int {
        var best = 1
        val cards = "AKQT98765432"
        for (rep in cards) {
            var newHand = ""
            for (c in hand) {
                if (c == 'J') newHand += rep
                else newHand += c
            }
            best = max(best, handRank(newHand))
        }
        return best
    }

    fun lexicographicalCards2(hand: String) : String {
        var newHand = ""
        for (c in hand) {
            if (c == 'A') newHand += "Z"
            else if (c == 'K') newHand += "Y"
            else if (c == 'Q') newHand += "X"
            else if (c == 'J') newHand += "0"
            else newHand += c
        }
        return newHand
    }

    fun compareHands2(): Comparator<Pair<String, Int>> {
        return Comparator { a, b ->
            val rankA = handRank2(a.first)
            val rankB = handRank2(b.first)

            when {
                rankA > rankB -> -1
                rankB > rankA -> 1
                lexicographicalCards2(a.first) > lexicographicalCards2(b.first) -> -1
                else -> 1
            }
        }
    }

    fun part2(input: List<String>): Int {
        val list = mutableListOf<Pair<String, Int>>()
        for (line in input) {
            val words = line.split(" ")
            list.add(Pair(words[0], words[1].toInt()))
        }
        list.sortWith(compareHands2())
        list.reverse()
        var ans = 0
        for (i in list.indices) {
            ans += list[i].second * (i+1)
        }
        return ans
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440)
    check(part2(testInput) == 5905)

    val input = readInput("Day07")
    part1(input).println()
    val input2 = readInput("Day07")
    part2(input2).println()
}
