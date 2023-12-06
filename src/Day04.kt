fun main() {
    fun part1(input: List<String>): Int {
        var ans = 0
        for (line in input) {
            val tmp = line.split(":")
            val words = tmp[1].split(" ")
            val winning = mutableSetOf<Int>()
            var points = 0
            var left = true
            for (i in 0..words.size-1) {
                if (words[i] == "") continue
                if (words[i] == "|") {
                    left = false
                    continue
                }
                val num = words[i].toInt()
                if (left) winning.add(num)
                else if (winning.contains(num)) {
                    if (points == 0) points = 1
                    else points *= 2
                }
            }
            ans += points
        }
        return ans
    }

    fun part2(input: List<String>): Int {
        var ans = 0
        val days = input.size
        val cards = IntArray(days+1) {1}
        for ((curDay, line) in input.withIndex()) {
            val tmp = line.split(":")
            val words = tmp[1].split(" ")
            val winning = mutableSetOf<Int>()
            var left = true
            var matches = 0
            for (i in 0..<words.size) {
                if (words[i] == "") continue
                if (words[i] == "|") {
                    left = false
                    continue
                }
                val num = words[i].toInt()
                if (left) winning.add(num)
                else if (winning.contains(num)) matches++
            }
            for (idx in 1..matches) {
                cards[curDay+idx] += cards[curDay]
            }
            ans += cards[curDay]
        }
        return ans
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    part1(input).println()
    val input2 = readInput("Day04")
    part2(input2).println()
}
