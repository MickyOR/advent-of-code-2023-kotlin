fun main() {
    val dr = arrayOf(-1, 0, 1, 0)
    val dc = arrayOf(0, 1, 0, -1)

    var dp = Array(150*150) { -1 }
    val g = Array(150*150) { mutableListOf<Int>() }
    var targetNode = 0

    fun f(u: Int, par: Int) : Int {
        if (dp[u] != -1) return dp[u]
        var ans = Int.MIN_VALUE/4
        if (u == targetNode) ans = 0
        for (v in g[u]) {
            if (v == par) continue
            ans = maxOf(ans, f(v, u))
        }
        dp[u] = ans + 1
        return dp[u]
    }
    
    fun part1(input: List<String>): Int {
        val n = input.size
        val m = input[0].length
        val id: (Int, Int) -> Int = { r, c -> r*m + c }
        for (i in 0 until n) {
            for (j in 0 until m) {
                dp[id(i, j)] = -1
                g[id(i, j)] = mutableListOf()
            }
        }
        for (r in 0 until n) {
            for (c in 0 until m) {
                if (input[r][c] == '#') continue
                for (k in 0 until 4) {
                    val nr = r + dr[k]
                    val nc = c + dc[k]
                    if (nr < 0 || nr >= n || nc < 0 || nc >= m) continue
                    if (input[nr][nc] == '#') continue
                    if (input[r][c] == '.') {
                        if (input[nr][nc] == '^' && k == 2) continue
                        if (input[nr][nc] == '>' && k == 3) continue
                        if (input[nr][nc] == 'v' && k == 0) continue
                        if (input[nr][nc] == '<' && k == 1) continue
                        g[id(r, c)].add(id(nr, nc))
                    }
                    // There are no two consecutive slopes in the input
                    if (input[r][c] == '^' && k == 0) g[id(r, c)].add(id(nr, nc))
                    if (input[r][c] == '>' && k == 1) g[id(r, c)].add(id(nr, nc))
                    if (input[r][c] == 'v' && k == 2) g[id(r, c)].add(id(nr, nc))
                    if (input[r][c] == '<' && k == 3) g[id(r, c)].add(id(nr, nc))
                }
            }
        }
        targetNode = id(n-1, m-2)
        val ans = f(id(0, 1), -1) - 1
        return ans
    }

    val g2 = Array(150*150) { mutableListOf<Pair<Int, Int>>() }
    val usedNodes = mutableSetOf<Int>()
    var bestDist = 0

    fun dfs(u: Int, curDist: Int) {
        if (bestDist < curDist && u == targetNode) bestDist = curDist
        usedNodes.add(u)
        for (e in g2[u]) {
            val v = e.first
            val w = e.second
            if (usedNodes.contains(v)) continue
            dfs(v, curDist + w)
        }
        usedNodes.remove(u)
    }

    fun part2(input: List<String>): Int {
        val n = input.size
        val m = input[0].length
        val id: (Int, Int) -> Int = { r, c -> r*m + c }
        targetNode = id(n-1, m-2)
        for (i in 0 until n) {
            for (j in 0 until m) {
                g2[id(i, j)] = mutableListOf()
            }
        }
        for (r in 0 until n) {
            for (c in 0 until m) {
                if (input[r][c] == '#') continue
                for (k in 0 until 4) {
                    val nr = r + dr[k]
                    val nc = c + dc[k]
                    if (nr < 0 || nr >= n || nc < 0 || nc >= m) continue
                    if (input[nr][nc] == '#') continue
                    g2[id(r, c)].add(Pair(id(nr, nc), 1))
                }
            }
        }
        while (true) {
            var changed = false
            for (i in 0 until n) {
                for (j in 0 until m) {
                    val u = id(i, j)
                    if (g2[u].size == 2) {
                        changed = true
                        val a = g2[u][0]
                        val b = g2[u][1]
                        val totW = a.second + b.second

                        g2[u].clear()
                        g2[a.first].removeIf { it.first == u }
                        g2[b.first].removeIf { it.first == u }

                        g2[a.first].add(Pair(b.first, totW))
                        g2[b.first].add(Pair(a.first, totW))
                    }
                }
            }
            if (!changed) break
        }
        usedNodes.clear()
        bestDist = 0
        dfs(id(0, 1), 0)
        return bestDist
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day23_test")
//    check(part1(testInput) == 94)
    check(part2(testInput) == 154)

    val input = readInput("Day23")
//    part1(input).println()
    val input2 = readInput("Day23")
    part2(input2).println()
}
