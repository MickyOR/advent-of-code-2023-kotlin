import java.util.SortedSet
import kotlin.Comparator
import kotlin.math.min

fun <T, U> stateComparator(
    firstComparator: Comparator<T>,
    secondComparator: Comparator<U>
): Comparator<Pair<T, U>> =
    compareBy(firstComparator) { p: Pair<T, U> -> p.first }
        .thenBy(secondComparator) { p: Pair<T, U> -> p.second }

fun main() {
    // North, East, South, West
    val dr = arrayOf(-1, 0, 1, 0)
    val dc = arrayOf(0, 1, 0, -1)

    fun part1(input: List<String>): Int {
        val n = input.size
        val m = input[0].length
        val dist = Array(n) { Array(m) { Array(4) { IntArray(4) { Int.MAX_VALUE/4 } } } }
        val s = sortedSetOf<Pair<Int, Pair<Pair<Int, Int>, Pair<Int, Int>>>>(
            comparator = stateComparator(reverseOrder(), stateComparator(stateComparator(naturalOrder(), naturalOrder()), stateComparator(naturalOrder(), naturalOrder())))
        ) // {dist, { {row, col}, {dir, consecutive} }}
        s.add(Pair(0, Pair(Pair(0, 0), Pair(1, 0))))
        dist[0][0][1][0] = 0
        while (s.isNotEmpty()) {
            val cur = s.first()
            val curD = cur.first
            val row = cur.second.first.first
            val col = cur.second.first.second
            val dir = cur.second.second.first
            val consecutive = cur.second.second.second
            s.remove(s.first())
            if (curD > dist[row][col][dir][consecutive]) continue
            for (k in 0 until 4) {
                if ((dir+2)%4 == k) continue
                if (k == dir && consecutive == 3) continue
                val newCons = if (k == dir) consecutive+1 else 1
                val nr = row + dr[k]
                val nc = col + dc[k]
                if (nr < 0 || nr >= n) continue
                if (nc < 0 || nc >= m) continue
                val newD = curD + input[nr][nc].digitToInt()
                if (newD < dist[nr][nc][k][newCons]) {
                    dist[nr][nc][k][newCons] = newD
                    s.add(Pair(newD, Pair(Pair(nr, nc), Pair(k, newCons))))
                }
            }
        }
        var ans = Int.MAX_VALUE
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                ans = min(ans, dist[n-1][m-1][i][j])
            }
        }
        ans.println()
        return ans
    }

    fun part2(input: List<String>): Int {
        // Slight modification to part 1, wrote it directly in C++
        return -1
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day17_test")
    check(part1(testInput) == 102)
    check(part2(testInput) == -1)

    val input = readInput("Day17")
    part1(input).println()
//    val input2 = readInput("Day1X_2")
//    part2(input2).println()
}
