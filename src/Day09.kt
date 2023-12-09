fun main() {
    fun part1(input: List<String>): Int {
        var ans = 0
        for (line in input) {
            var a = line.split(" ").map{it.toInt()}.toMutableList()
            var mat = mutableListOf<MutableList<Int>>()
            mat.add(a)
            while (true) {
                var allZero = true
                var nxt = mutableListOf<Int>()
                for (i in 0 until mat.last().size-1) {
                    nxt.add(mat.last()[i+1] - mat.last()[i])
                    if (nxt.last() != 0) allZero = false
                }
                mat.add(nxt)
                if (allZero) break
            }
            mat.last().add(0)
            for (i in mat.size-2 downTo 0) {
                mat[i].add( mat[i+1].last() + mat[i].last() )
            }
            ans += mat[0].last()
        }
        return ans
    }

    fun part2(input: List<String>): Int {
        var ans = 0
        for (line in input) {
            var a = line.split(" ").map{it.toInt()}.toMutableList()
            var mat = mutableListOf<MutableList<Int>>()
            mat.add(a)
            while (true) {
                var allZero = true
                var nxt = mutableListOf<Int>()
                for (i in 0 until mat.last().size-1) {
                    nxt.add(mat.last()[i+1] - mat.last()[i])
                    if (nxt.last() != 0) allZero = false
                }
                mat.add(nxt)
                if (allZero) break
            }
            for (i in mat.indices) {
                mat[i].reverse()
            }
            mat.last().add(0)
            for (i in mat.size-2 downTo 0) {
                mat[i].add( -mat[i+1].last() + mat[i].last() )
            }
            ans += mat[0].last()
        }
        return ans
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 114)
    check(part2(testInput) == 2)

    val input = readInput("Day09")
    part1(input).println()
    val input2 = readInput("Day09")
    part2(input2).println()
}
