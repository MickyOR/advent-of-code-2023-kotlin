/*
* It's expected to look for patterns or properties in the input as part of
* solving the problem. But for part 2 of this day it's just too much :|
* */

fun main() {
    fun part1(input: List<String>): Int {
        val g = mutableMapOf<String, MutableList<String>>()
        for (i in 2 until input.size) {
            val words = input[i].split(" ")
            val u = words[0]
            val x = words[2].substring(1, 4)
            val y = words[3].substring(0, 3)
            if (!g.containsKey(u)) g[u] = mutableListOf()
            g[u]?.add(x)
            g[u]?.add(y)
        }
        var ans = 0
        var node = "AAA"
        var idx = 0
        while (node != "ZZZ") {
            if (input[0][idx%input[0].length] == 'L') {
                node = g[node]!![0]
            }
            else {
                node = g[node]!![1]
            }
            idx++
            ans++
        }
        return ans
    }

    fun gcd(a: Long, b: Long): Long {
        if (b == 0L) return a
        return gcd(b, a%b)
    }

    fun part2(input: List<String>): Long {
        val g = mutableMapOf<String, MutableList<String>>()
        for (i in 2 until input.size) {
            val words = input[i].split(" ")
            val u = words[0]
            val x = words[2].substring(1, 4)
            val y = words[3].substring(0, 3)
            if (!g.containsKey(u)) g[u] = mutableListOf()
            g[u]?.add(x)
            g[u]?.add(y)
        }
        var ans = 1L
        val nodes = mutableListOf<String>()
        for (node in g.keys) {
            if (node.last() == 'A') nodes.add(node)
        }
        for (initNode in nodes) {
            var node = initNode
            var stepsToZ = 0L
            var idx = 0
            while (node.last() != 'Z') {
                node = g[node]!![if (input[0][idx] == 'L') 0 else 1]
                idx = (idx+1)%input[0].length
                stepsToZ++
            }
            ans = ans / gcd(ans, stepsToZ) * stepsToZ
        }
        return ans
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 2)
    val testInput2 = readInput("Day08_test2")
    check(part2(testInput2) == 6L)

    val input = readInput("Day08")
    part1(input).println()
    val input2 = readInput("Day08")
    part2(input2).println()
}
