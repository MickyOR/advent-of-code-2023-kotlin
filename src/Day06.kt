fun main() {
    fun part1(input: List<String>): Long {
        var timeList = input[0].split("\\s+".toRegex())
        var distList = input[1].split("\\s+".toRegex())
        timeList = timeList.drop(1)
        distList = distList.drop(1)
        var ans = 1L
        for (i in timeList.indices) {
            var ways = 0
            for (hold in 0..timeList[i].toInt()) {
                val timeLeft = timeList[i].toInt() - hold
                val dist = 1L * hold * timeLeft
                if (dist > distList[i].toInt()) ways++
            }
            ans *= ways
        }
        return ans
    }

    fun part2(input: List<String>): Long {
        var time = 0L
        var dist = 0L
        for (c in input[0]) {
            if (c.isDigit()) {
                time = time * 10 + c.digitToInt()
            }
        }
        for (c in input[1]) {
            if (c.isDigit()) {
                dist = dist * 10 + c.digitToInt()
            }
        }
        var ans = 0L
        for (hold in 0..<time) {
            val timeLeft = time - hold
            if (hold > dist / timeLeft) ans++
        }
        return ans
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288L)
    check(part2(testInput) == 71503L)

    val input = readInput("Day06")
    part1(input).println()
    val input2 = readInput("Day06")
    part2(input2).println()
}
