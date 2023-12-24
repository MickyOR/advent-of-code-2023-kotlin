import java.util.*
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.max

fun main() {
    val dr = arrayOf(-1, 0, 1, 0)
    val dc = arrayOf(0, 1, 0, -1)

    fun part1(input: List<String>): Int {
        val digPositions = mutableListOf<Pair<Int, Int>>()
        var row = 0
        var col = 0
        for (line in input) {
            val words = line.split(" ")
            val dir = if (words[0] == "U") 0 else if (words[0] == "R") 1 else if (words[0] == "D") 2 else 3
            val dist = words[1].toInt()
            for (ite in 1 .. dist) {
                row += dr[dir]
                col += dc[dir]
                digPositions.add(Pair(row, col))
            }
        }
        var minRow = Int.MAX_VALUE
        var maxRow = Int.MIN_VALUE
        var minCol = Int.MAX_VALUE
        var maxCol = Int.MIN_VALUE
        for (pos in digPositions) {
            minRow = min(minRow, pos.first)
            maxRow = max(maxRow, pos.first)
            minCol = min(minCol, pos.second)
            maxCol = max(maxCol, pos.second)
        }
        val n = maxRow - minRow + 3
        val m = maxCol - minCol + 3
        val mat = mutableListOf<MutableList<Char>>()
        for (i in 0 until n) {
            mat.add(mutableListOf())
            for (j in 0 until m) {
                mat[i].add('.')
            }
        }
        for (pos in digPositions) {
            mat[pos.first-minRow+1][pos.second-minCol+1] = '#'
        }
        val st: Stack<Pair<Int, Int>> = Stack()
        st.push(Pair(0, 0))
        while (st.isNotEmpty()) {
            val cur = st.pop()
            val row = cur.first
            val col = cur.second
            mat[row][col] = '@'
            for (k in 0 until 4) {
                val nr = row + dr[k]
                val nc = col + dc[k]
                if (nr < 0 || nr >= n || nc < 0 || nc >= m) continue
                if (mat[nr][nc] != '.') continue
                mat[nr][nc] = '@'
                st.push(Pair(nr, nc))
            }
        }
        var ans = 0
        for (i in 0 until n) {
            for (j in 0 until m) {
                if (mat[i][j] != '@') ans++
            }
        }
        return ans
    }

    fun part2(input: List<String>): Long {
        val digPositions = mutableListOf<Pair<Long, Long>>()
        var row = 0L
        var col = 0L
        digPositions.add(Pair(col, row))
        for (line in input) {
            val words = line.split(" ")
            val dir = when (words[2][7]) {
                '0' -> 1
                '1' -> 2
                '2' -> 3
                else -> 0
            }
            val dist = words[2].substring(2, 7).toLong(16)
            val nxtR = row + dr[dir]*dist
            val nxtC = col + dc[dir]*dist
            digPositions.add(Pair(nxtC, nxtR))
            row = nxtR
            col = nxtC
        }
        var ans = 0L
        var boundary = 0L
        for (i in 0 until digPositions.size-1) {
            ans += (digPositions[i].first*digPositions[i+1].second - digPositions[i+1].first*digPositions[i].second)
            boundary += abs(digPositions[i+1].first - digPositions[i].first) + abs(digPositions[i+1].second - digPositions[i].second)
        }
        val inside = ans/2 + 1 - boundary/2
        return boundary + inside
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day18_test")
    check(part1(testInput) == 62)
    check(part2(testInput) == 952408144115L)

    val input = readInput("Day18")
    part1(input).println()
    val input2 = readInput("Day18")
    part2(input2).println()
}
