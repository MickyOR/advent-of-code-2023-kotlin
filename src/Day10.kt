fun main() {
    fun part1(input: List<String>): Int {
        var r = -1
        var c = -1
        val vis = List(input.size) { MutableList(input[0].length) {false} }
        var loopLength = 0
        for (i in input.indices) {
            for (j in 0 until input[i].length) {
                if (input[i][j] == 'S') {
                    r = i
                    c = j
                }
            }
        }
        val dirs = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()

        dirs['S'] = mutableListOf()
        dirs['|'] = mutableListOf()
        dirs['-'] = mutableListOf()
        dirs['L'] = mutableListOf()
        dirs['J'] = mutableListOf()
        dirs['7'] = mutableListOf()
        dirs['F'] = mutableListOf()

        dirs['S']?.add(Pair(0, 1))
        dirs['S']?.add(Pair(1, 0))
        dirs['F']?.add(Pair(0, 1))
        dirs['F']?.add(Pair(1, 0))

        dirs['|']?.add(Pair(-1, 0))
        dirs['|']?.add(Pair(1, 0))

        dirs['-']?.add(Pair(0, -1))
        dirs['-']?.add(Pair(0, 1))

        dirs['L']?.add(Pair(-1, 0))
        dirs['L']?.add(Pair(0, 1))

        dirs['J']?.add(Pair(-1, 0))
        dirs['J']?.add(Pair(0, -1))

        dirs['7']?.add(Pair(0, -1))
        dirs['7']?.add(Pair(1, 0))

        while (!vis[r][c]) {
            vis[r][c] = true
            for (dir in dirs[input[r][c]]!!) {
                val nr = r + dir.first
                val nc = c + dir.second
                if (!vis[nr][nc]) {
                    r = nr
                    c = nc
                    loopLength++
                    break
                }
            }
        }
        return (loopLength+1)/2
    }

    fun part2(input: List<String>): Int {
        var r = -1
        var c = -1
        val vis = List(input.size) { MutableList(input[0].length) {false} }
        for (i in input.indices) {
            for (j in 0 until input[i].length) {
                if (input[i][j] == 'S') {
                    r = i
                    c = j
                }
            }
        }
        val dirs = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()

        dirs['S'] = mutableListOf()
        dirs['|'] = mutableListOf()
        dirs['-'] = mutableListOf()
        dirs['L'] = mutableListOf()
        dirs['J'] = mutableListOf()
        dirs['7'] = mutableListOf()
        dirs['F'] = mutableListOf()

        dirs['S']?.add(Pair(0, 1))
        dirs['S']?.add(Pair(1, 0))
        dirs['F']?.add(Pair(0, 1))
        dirs['F']?.add(Pair(1, 0))

        dirs['|']?.add(Pair(-1, 0))
        dirs['|']?.add(Pair(1, 0))

        dirs['-']?.add(Pair(0, -1))
        dirs['-']?.add(Pair(0, 1))

        dirs['L']?.add(Pair(-1, 0))
        dirs['L']?.add(Pair(0, 1))

        dirs['J']?.add(Pair(-1, 0))
        dirs['J']?.add(Pair(0, -1))

        dirs['7']?.add(Pair(0, -1))
        dirs['7']?.add(Pair(1, 0))

        while (!vis[r][c]) {
            vis[r][c] = true
            for (dir in dirs[input[r][c]]!!) {
                val nr = r + dir.first
                val nc = c + dir.second
                if (!vis[nr][nc]) {
                    r = nr
                    c = nc
                    break
                }
            }
        }
        var ans = 0
        for (i in 0 until input.size) {
            var cnt = 0
            for (j in 0 until input[i].length) {
                if (vis[i][j] && input[i][j] == '|') cnt++
                if (vis[i][j] && input[i][j] == 'L') cnt++
                if (vis[i][j] && input[i][j] == 'J') cnt++
                if (!vis[i][j] && cnt%2 == 1) ans++
            }
        }
        return ans
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 8)
    val testInput2 = readInput("Day10_test2")
    check(part2(testInput2) == 4)

    val input = readInput("Day10")
    part1(input).println()
    val input2 = readInput("Day10")
    part2(input2).println()
}
