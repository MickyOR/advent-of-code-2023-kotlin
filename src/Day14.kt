import java.lang.StringBuilder
import java.math.BigInteger

fun main() {
    fun part1(input: List<String>): Int {
        var ans = 0
        val mat = mutableListOf<CharArray>()
        for (line in input) {
            mat.add(line.toCharArray())
        }
        val n = input.size
        val m = input[0].length
        for (i in 0 until n) {
            for (j in 0 until m) {
                if (mat[i][j] == 'O') {
                    var tmp = i-1
                    while (tmp >= 0 && mat[tmp][j] == '.') {
                        mat[tmp+1][j] = '.'
                        mat[tmp][j] = 'O'
                        tmp--
                    }
                }
            }
        }
        for (i in 0 until n) {
            for (j in 0 until m) {
                if (mat[i][j] == 'O') ans += n-i
            }
        }
        return ans
    }

    fun cycle(mat: MutableList<CharArray>): MutableList<CharArray> {
        val n = mat.size
        val m = mat[0].size
        // north
        for (i in 0 until n) {
            for (j in 0 until m) {
                if (mat[i][j] == 'O') {
                    var tmp = i-1
                    while (tmp >= 0 && mat[tmp][j] == '.') {
                        mat[tmp+1][j] = '.'
                        mat[tmp][j] = 'O'
                        tmp--
                    }
                }
            }
        }
        // west
        for (j in 0 until m) {
            for (i in 0 until n) {
                if (mat[i][j] == 'O') {
                    var tmp = j-1
                    while (tmp >= 0 && mat[i][tmp] == '.') {
                        mat[i][tmp] = 'O'
                        mat[i][tmp+1] = '.'
                        tmp--
                    }
                }
            }
        }
        // south
        for (i in n-1 downTo 0) {
            for (j in 0 until m) {
                if (mat[i][j] == 'O') {
                    var tmp = i+1
                    while (tmp < n && mat[tmp][j] == '.') {
                        mat[tmp-1][j] = '.'
                        mat[tmp][j] = 'O'
                        tmp++
                    }
                }
            }
        }
        // east
        for (j in m-1 downTo 0) {
            for (i in 0 until n) {
                if (mat[i][j] == 'O') {
                    var tmp = j+1
                    while (tmp < m && mat[i][tmp] == '.') {
                        mat[i][tmp] = 'O'
                        mat[i][tmp-1] = '.'
                        tmp++
                    }
                }
            }
        }
        return mat
    }

    fun mapHash(mat: MutableList<CharArray>): String {
        val s = StringBuilder("");
        for (line in mat) {
            s.append(line)
        }
        return s.toString().md5()
    }

    fun part2(input: List<String>): Int {
        val vis = mutableMapOf<String, Int>()
        var mat = mutableListOf<CharArray>()
        for (line in input) {
            mat.add(line.toCharArray())
        }
        var cur = mapHash(mat)
        vis.putIfAbsent(cur, 1)
        var moves = 1000000000
        var cycleLength = 0
        var shrinked = false
        while (moves > 0) {
            moves--
            mat = cycle(mat)
            cur = mapHash(mat)
            if (!vis.containsKey(cur)) {
                vis.putIfAbsent(cur, 1)
            }
            else if (!shrinked) {
                if (vis[cur] == 1) {
                    cycleLength++
                    vis[cur] = 2
                }
                else if (vis[cur] == 2) {
                    moves %= cycleLength
                    shrinked = true
                }
            }
            else vis[cur]?.plus(1)
        }
        var ans = 0
        for (i in 0 until mat.size) {
            for (j in 0 until mat[0].size) {
                if (mat[i][j] == 'O') ans += mat.size-i
            }
        }
        return ans
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part1(testInput) == 136)
    check(part2(testInput) == 64)

    val input = readInput("Day14")
    part1(input).println()
    val input2 = readInput("Day14")
    part2(input2).println()
}
