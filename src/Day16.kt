import java.util.*
import kotlin.math.max

fun main() {
    // North, East, South, West
    val dr = arrayOf(-1, 0, 1, 0)
    val dc = arrayOf(0, 1, 0, -1)

    fun moves(cell: Char, dir: Int): MutableList<Int> {
        val ans = mutableListOf<Int>()
        // empty cell
        if (cell == '.') ans.add(dir)

        // pass through splitter
        if ((dir == 0 || dir == 2) && cell == '|') ans.add(dir)
        if ((dir == 1 || dir == 3) && cell == '-') ans.add(dir)

        // split
        if ((dir == 0 || dir == 2) && cell == '-') {
            ans.add(1)
            ans.add(3)
        }
        if ((dir == 1 || dir == 3) && cell == '|') {
            ans.add(0)
            ans.add(2)
        }

        // mirror
        if (cell == '\\') {
            if (dir == 0) ans.add(3)
            if (dir == 1) ans.add(2)
            if (dir == 2) ans.add(1)
            if (dir == 3) ans.add(0)
        }
        if (cell == '/') {
            if (dir == 0) ans.add(1)
            if (dir == 1) ans.add(0)
            if (dir == 2) ans.add(3)
            if (dir == 3) ans.add(2)
        }
        return ans
    }

    fun part1(input: List<String>): Int {
        val n = input.size
        val m = input[0].length
        val vis = Array(n) { Array(m) { BooleanArray(4) {false} } }
        val queue: Queue<Pair<Pair<Int, Int>, Int>> = LinkedList()
        queue.add(Pair(Pair(0, 0), 1))
        vis[0][0][1] = true
        while (queue.isNotEmpty()) {
            val cur = queue.poll()
            val r = cur.first.first
            val c = cur.first.second
            val d = cur.second
            val avMoves = moves(input[r][c], d)
            for (move in avMoves) {
                val nr = r + dr[move]
                val nc = c + dc[move]
                if (nr < 0 || nr >= n) continue
                if (nc < 0 || nc >= m) continue
                if (vis[nr][nc][move]) continue
                vis[nr][nc][move] = true
                queue.add(Pair(Pair(nr, nc), move))
            }
        }
        var ans = 0
        for (i in 0 until n) {
            for (j in 0 until m) {
                for (k in 0 until 4) {
                    if (vis[i][j][k]) {
                        ans++
                        break
                    }
                }
            }
        }
        return ans
    }

    fun countEnergized(input: List<String>, sr: Int, sc: Int, dir: Int): Int {
        val n = input.size
        val m = input[0].length
        val vis = Array(n) { Array(m) { BooleanArray(4) {false} } }
        val queue: Queue<Pair<Pair<Int, Int>, Int>> = LinkedList()
        queue.add(Pair(Pair(sr, sc), dir))
        vis[sr][sc][dir] = true
        while (queue.isNotEmpty()) {
            val cur = queue.poll()
            val r = cur.first.first
            val c = cur.first.second
            val d = cur.second
            val avMoves = moves(input[r][c], d)
            for (move in avMoves) {
                val nr = r + dr[move]
                val nc = c + dc[move]
                if (nr < 0 || nr >= n) continue
                if (nc < 0 || nc >= m) continue
                if (vis[nr][nc][move]) continue
                vis[nr][nc][move] = true
                queue.add(Pair(Pair(nr, nc), move))
            }
        }
        var ans = 0
        for (i in 0 until n) {
            for (j in 0 until m) {
                for (k in 0 until 4) {
                    if (vis[i][j][k]) {
                        ans++
                        break
                    }
                }
            }
        }
        return ans
    }

    fun part2(input: List<String>): Int {
        var ans = 0
        val n = input.size
        val m = input[0].length
        for (j in 0 until m) {
            ans = max(ans, countEnergized(input, 0, j, 2))
            ans = max(ans, countEnergized(input, n-1, j, 0))
        }
        for (i in 0 until n) {
            ans = max(ans, countEnergized(input, i, 0, 1))
            ans = max(ans, countEnergized(input, i, m-1, 3))
        }
        return ans
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day16_test")
    check(part1(testInput) == 46)
    check(part2(testInput) == 51)

    val input = readInput("Day16")
    part1(input).println()
    val input2 = readInput("Day16")
    part2(input2).println()
}
