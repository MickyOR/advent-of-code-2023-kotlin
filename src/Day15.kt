import java.util.LinkedList

fun main() {
    val p = 17
    val mod = 256
    fun hash(input: String): Int {
        var ans = 0
        for (c in input) {
            ans += c.code
            ans *= p
            ans %= mod
        }
        return ans
    }

    fun part1(input: List<String>): Int {
        val words = input[0].split(",")
        var ans = 0
        for (word in words) {
            ans += hash(word)
        }
        return ans
    }

    fun searchId(list: LinkedList<Pair<String, Int>>, label: String): Int {
        for (i in list.indices) {
            if (list[i].first == label) return i
        }
        return -1
    }

    fun part2(input: List<String>): Int {
        val instructions = input[0].split(",")
        val boxes = Array(256) { LinkedList<Pair<String, Int>>() }
        for (op in instructions) {
            if (op.last() == '-') {
                val aux = op.dropLast(1)
                val boxNo = hash(aux)
                val posInBox = searchId(boxes[boxNo], aux)
                if (posInBox != -1) {
                    boxes[boxNo].removeAt(posInBox)
                }
            }
            else {
                val aux = op.split("=")
                val label = aux[0]
                val value = aux[1].toInt()
                val boxId = hash(label)
                val posInBox = searchId(boxes[boxId], label)
                if (posInBox != -1) {
                    boxes[boxId][posInBox] = Pair(label, value)
                }
                else {
                    boxes[boxId].add(Pair(label, value))
                }
            }
        }
        var ans = 0
        for (i in 0 until boxes.size) {
            for (j in boxes[i].indices) {
                ans += (i+1) * (j+1) * boxes[i][j].second
            }
        }
        return ans
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day15_test")
    check(part1(testInput) == 1320)
    check(part2(testInput) == 145)

    val input = readInput("Day15")
    part1(input).println()
    val input2 = readInput("Day15")
    part2(input2).println()
}
