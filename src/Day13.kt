import javax.print.attribute.standard.PrinterLocation

fun main() {
    fun solve1(input: MutableList<String>): Int {
        var ans = 0
        // check columns
        for (j in 0 until input[0].length-1) {
            var mirrored = true
            for (i in 0 until input.size) {
                if (!mirrored) break
                for (k in 0 until input[0].length) {
                    if (!mirrored) break
                    val l = j - k
                    val r = j + 1 + k
                    if (l < 0 || r >= input[0].length) break
                    if (input[i][l] != input[i][r]) mirrored = false
                }
            }
            if (mirrored) {
                ans += j+1
            }
        }
        // check rows
        for (i in 0 until input.size-1) {
            var mirrored = true
            for (j in 0 until input[0].length) {
                if (!mirrored) break
                for (k in 0 until input.size) {
                    if (!mirrored) break
                    val u = i - k
                    val d = i + 1 + k
                    if (u < 0 || d >= input.size) break
                    if (input[u][j] != input[d][j]) mirrored = false
                }
            }
            if (mirrored) {
                ans += 100 * (i+1)
            }
        }
        return ans
    }

    fun part1(input: List<String>): Int {
        val lava = mutableListOf<String>()
        var ans = 0
        for (line in input) {
            if (line == "") {
                ans += solve1(lava)
                lava.clear()
            }
            else lava.add(line)
        }
        ans += solve1(lava)
        return ans
    }

    fun solve2(input: MutableList<String>): Int {
        var ans = 0
        // check columns
        for (j in 0 until input[0].length-1) {
            var diff = 0
            for (i in 0 until input.size) {
                if (diff > 1) break
                for (k in 0 until input[0].length) {
                    if (diff > 1) break
                    val l = j - k
                    val r = j + 1 + k
                    if (l < 0 || r >= input[0].length) break
                    if (input[i][l] != input[i][r]) diff++
                }
            }
            if (diff == 1) {
                ans += j+1
            }
        }
        // check rows
        for (i in 0 until input.size-1) {
            var diff = 0
            for (j in 0 until input[0].length) {
                if (diff > 1) break
                for (k in 0 until input.size) {
                    if (diff > 1) break
                    val u = i - k
                    val d = i + 1 + k
                    if (u < 0 || d >= input.size) break
                    if (input[u][j] != input[d][j]) diff++
                }
            }
            if (diff == 1) {
                ans += 100 * (i+1)
            }
        }
        return ans
    }

    fun part2(input: List<String>): Int {
        val lava = mutableListOf<String>()
        var ans = 0
        for (line in input) {
            if (line == "") {
                ans += solve2(lava)
                lava.clear()
            }
            else lava.add(line)
        }
        ans += solve2(lava)
        return ans
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test")
    check(part1(testInput) == 405)
    check(part2(testInput) == 400)

    val input = readInput("Day13")
    part1(input).println()
    val input2 = readInput("Day13")
    part2(input2).println()
}
