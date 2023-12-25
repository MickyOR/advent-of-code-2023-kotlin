import java.util.*

fun main() {
    val dr = arrayOf(-1, 0, 1, 0)
    val dc = arrayOf(0, 1, 0, -1)

    fun part1(input: List<String>): Int {
        val n = input.size
        val m = input[0].length
        val vis = Array(n) { Array(m) { BooleanArray(65) { false } } }
        val q: Queue<Pair<Pair<Int, Int>, Int>> = LinkedList()
        for (i in 0 until n) {
            for (j in 0 until m) {
                if (input[i][j] == 'S') {
                    q.add(Pair(Pair(i, j), 0))
                    vis[i][j][0] = true
                }
            }
        }
        while (q.isNotEmpty()) {
            val cur = q.poll()
            val r = cur.first.first
            val c = cur.first.second
            val dist = cur.second
            if (dist == 64) continue
            for (i in 0 until 4) {
                val nr = r + dr[i]
                val nc = c + dc[i]
                if (nr < 0 || nr >= n || nc < 0 || nc >= m) continue
                if (input[nr][nc] == '#') continue
                if (vis[nr][nc][dist + 1]) continue
                vis[nr][nc][dist + 1] = true
                q.add(Pair(Pair(nr, nc), dist + 1))
            }
        }
        var ans = 0
        for (i in 0 until n) {
            for (j in 0 until m) {
                if (vis[i][j][64]) ans++
            }
        }
        return ans
    }

    fun part2(input: List<String>): Int {
        val n = input.size
        val m = input[0].length
        val vis = mutableMapOf<Int, MutableMap<Int, MutableMap<Int, Boolean>>>()
        val q: Queue<Pair<Pair<Int, Int>, Int>> = LinkedList()
        for (i in 0 until n) {
            for (j in 0 until m) {
                if (input[i][j] == 'S') {
                    q.add(Pair(Pair(i, j), 0))
                    vis.putIfAbsent(i, mutableMapOf())
                    vis[i]!!.putIfAbsent(j, mutableMapOf())
                    vis[i]!![j]!!.putIfAbsent(0, false)
                    vis[i]!![j]!![0] = true
                }
            }
        }
        val points = arrayOf(65, 65+131, 65+131*2)
        val pointCnt = mutableMapOf<Int, Int>()
        for (point in points) pointCnt[point] = 0
        while (q.isNotEmpty()) {
            val cur = q.poll()
            val r = cur.first.first
            val c = cur.first.second
            val dist = cur.second
            if (pointCnt.containsKey(dist)) pointCnt[dist] = pointCnt[dist]!! + 1
            if (dist >= 327) continue
            for (i in 0 until 4) {
                val nr = r + dr[i]
                val nc = c + dc[i]
                if (input[(nr%n+n)%n][(nc%m+m)%m] == '#') continue

                vis.putIfAbsent(nr, mutableMapOf())
                vis[nr]!!.putIfAbsent(nc, mutableMapOf())
                vis[nr]!![nc]!!.putIfAbsent(dist + 1, false)

                if (vis[nr]!![nc]!![dist + 1]!!) continue
                vis[nr]!![nc]!![dist + 1] = true
                q.add(Pair(Pair(nr, nc), dist + 1))
            }
        }
        pointCnt.println()
        return -1
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day21_test")
//    check(part1(testInput) == 16)
//    check(part2(testInput) == -1)

    val input = readInput("Day21")
    part1(input).println()
    val input2 = readInput("Day21")
    part2(input2).println()
}
