fun main() {
    val dr = arrayOf(-1, 0, 1, -1, 1, -1, 0, 1)
    val dc = arrayOf(-1, -1, -1, 0, 0, 1, 1, 1)

    fun part1(input: List<String>): Int {
        var ans = 0
        for (r in input.indices) {
            var goodNumber = false
            var number = 0
            for (c in input[r].indices) {
                if (input[r][c].isDigit()) {
                    number = number * 10 + input[r][c].digitToInt()
                    for (k in 0..7) {
                        val nr = r + dr[k]
                        val nc = c + dc[k]
                        if (nr < 0 || nr >= input.size || nc < 0 || nc >= input[r].length) continue
                        if (!input[nr][nc].isDigit() && input[nr][nc] != '.') goodNumber = true
                    }
                }
                if (!input[r][c].isDigit() || c == input[r].length-1) {
                    if (goodNumber) ans += number
                    number = 0
                    goodNumber = false
                }
            }
        }
        return ans
    }

    fun part2(input: List<String>): Int {
        val n = input.size
        val m = input[0].length
        var mark = Array(n) { Array(m) { Pair(-1, -1) } }
        var cnt = 0
        var ans = 0
        // mark cells with numbers and ids
        for (r in input.indices) {
            var number = 0
            for (c in input[r].indices) {
                if (input[r][c].isDigit()) {
                    number = number * 10 + input[r][c].digitToInt()
                }
                if (!input[r][c].isDigit() || c == input[r].length-1) {
                    if ((c > 0 && input[r][c-1].isDigit()) || input[r][c].isDigit()) {
                        for (i in c downTo 0) {
                            if (i == c && !input[r][i].isDigit()) continue
                            if (!input[r][i].isDigit()) break
                            mark[r][i] = Pair(number, cnt)
                        }
                    }
                    cnt++
                    number = 0
                }
            }
        }
        // check for gears
        for (r in input.indices) {
            for (c in input[r].indices) {
                if (input[r][c] != '*') continue
                val adjNumbers = mutableSetOf<Int>()
                var ratio = 1
                for (k in 0..7) {
                    val nr = r + dr[k]
                    val nc = c + dc[k]
                    if (nr < 0 || nr >= input.size) continue
                    if (nc < 0 || nc >= input[r].length) continue
                    if (mark[nr][nc].second == -1) continue
                    if (!adjNumbers.contains(mark[nr][nc].second)) {
                        ratio *= mark[nr][nc].first
                        adjNumbers.add(mark[nr][nc].second)
                    }
                }
                if (adjNumbers.size == 2) {
                    ans += ratio
                }
            }
        }
        return ans
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    part1(input).println()
    val input2 = readInput("Day03")
    part2(input2).println()
}
