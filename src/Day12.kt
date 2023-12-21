import java.lang.StringBuilder
import kotlin.math.max

fun main() {

    // Could be solved with dp but there are at most 18 question marks in the input
    fun part1(input: List<String>): Int {
        var ans = 0
        for (line in input) {
            val aux = line.split(" ")
            val map = aux[0]
            val segments = aux[1].split(",").map { it.toInt() }.toIntArray()
            val positions = mutableListOf<Int>()
            for (i in 0 until map.length) {
                if (map[i] == '?') positions.add(i)
            }
            for (mask in 0 until (1 shl positions.size)) {
                val newMap = StringBuilder(map)
                for (i in 0 until positions.size) {
                    if ( (mask shr i) and 1 == 1) {
                        newMap.setCharAt(positions[i], '#')
                    }
                    else {
                        newMap.setCharAt(positions[i], '.')
                    }
                }
                val newSegs = mutableListOf<Int>()
                var curLen = 0
                for (c in newMap.toString()) {
                    if (c == '.') {
                        if (curLen > 0) {
                            newSegs.add(curLen)
                        }
                        curLen = 0
                    }
                    else curLen++
                }
                if (curLen > 0) newSegs.add(curLen)
                if (segments.contentEquals(newSegs.toIntArray())) {
                    ans++
                }
            }
        }
        return ans;
    }

    // Ok, now it's dp lmao
    val dp = Array(250) { Array(250) { LongArray(100) { -1L } } }
    val segmentList = mutableListOf<Int>()
    var newMap = ""

    fun f(pos: Int, consecutive: Int, posInList: Int): Long {
        if (pos == newMap.length) {
            if (posInList == segmentList.size) return 1
            else return 0
        }
        if (dp[pos][consecutive][posInList] != -1L) return dp[pos][consecutive][posInList]
        var ans = 0L
        if (newMap[pos] == '.' || newMap[pos] == '?') {
            if (consecutive > 0 && posInList < segmentList.size && consecutive == segmentList[posInList]) {
                ans += f(pos+1, 0, posInList+1)
            }
            if (consecutive == 0) {
                ans += f(pos+1, 0, posInList)
            }
        }
        if (newMap[pos] == '#' || newMap[pos] == '?') {
            ans += f(pos+1, consecutive+1, posInList)
        }
        dp[pos][consecutive][posInList] = ans
        return ans
    }

    fun part2(input: List<String>): Long {
        var ans = 0L
        for (line in input) {
            segmentList.clear()
            val aux = line.split(" ")
            val map = aux[0]
            val segments = aux[1].split(",").map { it.toInt() }.toMutableList()
            val unfolded = StringBuilder("")
            for (ite in 1 .. 5) {
                unfolded.append(map)
                if (ite < 5) unfolded.append("?")
                for (x in segments) {
                    segmentList.add(x)
                }
            }
            unfolded.append(".")
            newMap = unfolded.toString()
            for (i in 0 until 250) {
                for (j in 0 until 250) {
                    for (k in 0 until 100) {
                        dp[i][j][k] = -1L
                    }
                }
            }
            var arrangments = f(0, 0, 0)
            ans += arrangments
        }
        return ans
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 525152L)

    val input = readInput("Day12")
    part1(input).println()
    val input2 = readInput("Day12")
    part2(input2).println()
}
