import kotlin.math.max

fun main() {
    fun part1(input: List<String>): Int {
        var ans = 0
        var cnt = 0
        for (line in input) {
            var good = true
            cnt++
            var words = line.split(" ")
            var red = 0
            var green = 0
            var blue = 0
            var idx = 2
            while (idx < words.size) {
                val amount = words[idx].toInt()
                var color = words[idx+1]
                idx += 2
                val reset = color.last() == ';'
                if (color.last() == ',' || color.last() == ';') {
                    color = color.dropLast(1)
                }
                if (color == "red") red += amount
                if (color == "green") green += amount
                if (color == "blue") blue += amount
                if (red > 12 || green > 13 || blue > 14) good = false
                if (reset) {
                    red = 0
                    green = 0
                    blue = 0
                }
            }
            if (good) ans += cnt
        }
        return ans
    }

    fun part2(input: List<String>): Int {
        var ans = 0
        for (line in input) {
            var words = line.split(" ")
            var red = 0
            var green = 0
            var blue = 0
            var minRed = 0
            var minGreen = 0
            var minBlue = 0
            var idx = 2
            while (idx < words.size) {
                val amount = words[idx].toInt()
                var color = words[idx+1]
                idx += 2
                val reset = color.last() != ','
                if (color.last() == ',' || color.last() == ';') {
                    color = color.dropLast(1)
                }
                if (color == "red") red += amount
                if (color == "green") green += amount
                if (color == "blue") blue += amount
                if (reset) {
                    minRed = max(minRed, red)
                    minGreen = max(minGreen, green)
                    minBlue = max(minBlue, blue)
                    red = 0
                    green = 0
                    blue = 0
                }
            }
            ans += minRed * minGreen * minBlue
        }
        return ans
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    val input2 = readInput("Day02")
    part2(input2).println()
}
