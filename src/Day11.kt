import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    fun expand(universe: List<String>): MutableList<String> {
        var ans = mutableListOf<String>()
        val n = universe.size
        val m = universe[0].length
        val emptyCols = BooleanArray(m)
        for (i in 0 until m) {
            var cnt = 0
            for (j in 0 until n) {
                if (universe[j][i] != '.') cnt++
            }
            if (cnt == 0) emptyCols[i] = true
        }
        for (i in 0 until n) {
            var line = ""
            for (j in 0 until m) {
                if (emptyCols[j]) line += ".."
                else line += universe[i][j]
            }
            ans.add(line)
        }
        var ansForReal = mutableListOf<String>()
        val emptyRows = BooleanArray(n)
        for (i in 0 until n) {
            var cnt = 0
            for (j in 0 until m) {
                if (universe[i][j] != '.') cnt++
            }
            if (cnt == 0) emptyRows[i] = true
        }
        for (i in 0 until n) {
            if (emptyRows[i]) ansForReal.add(ans[i])
            ansForReal.add(ans[i])
        }
        return ansForReal
    }

    fun part1(input: List<String>): Int {
        val expanded = expand(input)
        val stars = mutableListOf<Pair<Int, Int>>()
        for (i in 0 until expanded.size) {
            for (j in 0 until expanded[i].length) {
                if (expanded[i][j] == '#') stars.add(Pair(i, j))
            }
        }
        var ans = 0
        for (i in 0 until stars.size) {
            for (j in i+1 until stars.size) {
                ans += abs(stars[i].first - stars[j].first) + abs(stars[i].second - stars[j].second)
            }
        }
        return ans
    }

    fun part2(input: List<String>): Long {
        val stars = mutableListOf<Pair<Long, Long>>()
        for (i in 0 until input.size) {
            for (j in 0 until input[i].length) {
                if (input[i][j] == '#') stars.add(Pair(i.toLong(), j.toLong()))
            }
        }
        val n = input.size
        val m = input[0].length
        val emptyCols = BooleanArray(m)
        val emptyRows = BooleanArray(n)
        for (j in 0 until m) {
            var cnt = 0
            for (i in 0 until n) {
                if (input[i][j] != '.') cnt++
            }
            if (cnt == 0) emptyCols[j] = true
        }
        for (i in 0 until n) {
            var cnt = 0
            for (j in 0 until m) {
                if (input[i][j] != '.') cnt++
            }
            if (cnt == 0) emptyRows[i] = true
        }
        var ans = 0L
//        val expansion = 100
        val expansion = 1000000
        for (i in 0 until stars.size) {
            for (j in i+1 until stars.size) {
                var dist = abs(stars[i].first - stars[j].first) + abs(stars[i].second - stars[j].second)
                for (k in min(stars[i].first, stars[j].first) .. max(stars[i].first, stars[j].first)) {
                    if (emptyRows[k.toInt()]) dist += expansion-1
                }
                for (k in min(stars[i].second, stars[j].second) .. max(stars[i].second, stars[j].second)) {
                    if (emptyCols[k.toInt()]) dist += expansion-1
                }
                ans += dist
            }
        }
        return ans
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 374)
//    check(part2(testInput) == 8410)

    val input = readInput("Day11")
    part1(input).println()
    val input2 = readInput("Day11")
    part2(input2).println()
}
