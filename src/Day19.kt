import java.util.*

class Rule(val ruleString: String) {
    var label: String? = null
    var ruleList: MutableList<String>? = null

    fun parseRules() {
        val aux = ruleString.split("{")
        label = aux[0]
        ruleList = aux[1].dropLast(1).split(",").toMutableList()
    }

    fun applyRule(rule: String, data: MutableMap<Char, Int>): String {
        if (!rule.contains(":")) return rule
        val aux = rule.split(":")
        val input = rule[0]
        val ruleOp = rule[1]
        val value = aux[0].substring(2).toInt()
        return if (ruleOp == '>') {
            if (data[input]!! > value) aux[1]
            else "_"
        } else {
            if (data[input]!! < value) aux[1]
            else "_"
        }
    }
}

class Range(val xLo: Int, val xHi: Int, val mLo: Int, val mHi: Int, val aLo: Int, val aHi: Int, val sLo: Int, val sHi: Int) {
    fun inRangeX(x: Int): Boolean {
        return x in xLo..xHi
    }
    fun inRangeM(m: Int): Boolean {
        return m in mLo..mHi
    }
    fun inRangeA(a: Int): Boolean {
        return a in aLo..aHi
    }
    fun inRangeS(s: Int): Boolean {
        return s in sLo..sHi
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        var ans = 0
        var rules = true
        val ruleList = mutableListOf<Rule>()
        val labelPos = mutableMapOf<String, Int>() // position of label in ruleList
        for (line in input) {
            if (line.isEmpty()) {
                rules = false
                continue
            }
            if (rules) {
                val rule = Rule(line)
                rule.parseRules()
                labelPos.putIfAbsent(rule.label!!, ruleList.size)
                ruleList.add(rule)
            }
            else {
                val aux1 = line.substring(1)
                val aux2 = aux1.dropLast(1)
                val words = aux2.split(",")
                val data = mutableMapOf<Char, Int>()
                for (word in words) {
                    val aux = word.split("=")
                    data[aux[0][0]] = aux[1].toInt()
                }
                var result = "in"
                while (result != "A" && result != "R") {
                    val idx = labelPos[result]
                    for (rule in ruleList[idx!!].ruleList!!) {
                        val res = ruleList[idx].applyRule(rule, data)
                        if (res != "_") {
                            result = res
                            break
                        }
                    }
                }
                if (result == "A") {
                    for (it in data) {
                        ans += it.value
                    }
                }
            }
        }
        return ans
    }

    fun part2(input: List<String>): Long {
        // Let's just simulate it
        // It could take forever on an edge case but there are a lot of As and Rs in the input
        // *After getting the star: Not proud of how ugly the code turned out
        var ans = 0L
        val ruleList = mutableListOf<Rule>()
        val labelPos = mutableMapOf<String, Int>() // position of label in ruleList
        for (line in input) {
            if (line.isEmpty()) break
            val rule = Rule(line)
            rule.parseRules()
            labelPos.putIfAbsent(rule.label!!, ruleList.size)
            ruleList.add(rule)
        }
        val curRange = Range(1, 4000, 1, 4000, 1, 4000, 1, 4000)
        val rangeQueue: Queue<Pair<String, Range>> = LinkedList()
        rangeQueue.add(Pair("in", curRange))
        while (rangeQueue.isNotEmpty()) {
            val cur = rangeQueue.poll()
            var r = cur.second
            if (cur.first == "R") continue
            if (cur.first == "A") {
                ans += (r.xHi - r.xLo + 1L) * (r.mHi - r.mLo + 1L) * (r.aHi - r.aLo + 1L) * (r.sHi - r.sLo + 1L)
                continue
            }
            val idx = labelPos[cur.first]
            for (rule in ruleList[idx!!].ruleList!!) {
                if (!rule.contains(":")) {
                    rangeQueue.add(Pair(rule, r))
                    break
                }
                val aux = rule.split(":")
                val input = rule[0]
                val ruleOp = rule[1]
                val value = aux[0].substring(2).toInt()
                if (input == 'x') {
                    if (ruleOp == '>') {
                        if (!r.inRangeX(value)) {
                            if (r.xLo > value) {
                                rangeQueue.add(Pair(aux[1], Range(r.xLo, r.xHi, r.mLo, r.mHi, r.aLo, r.aHi, r.sLo, r.sHi)))
                                break
                            }
                        }
                        else {
                            val rLow = Range(r.xLo, value, r.mLo, r.mHi, r.aLo, r.aHi, r.sLo, r.sHi)
                            val rHigh = Range(value + 1, r.xHi, r.mLo, r.mHi, r.aLo, r.aHi, r.sLo, r.sHi)
                            rangeQueue.add(Pair(aux[1], rHigh))
                            r = rLow
                        }
                    }
                    else {
                        if (!r.inRangeX(value)) {
                            if (r.xHi < value) {
                                rangeQueue.add(Pair(aux[1], Range(r.xLo, r.xHi, r.mLo, r.mHi, r.aLo, r.aHi, r.sLo, r.sHi)))
                                break
                            }
                        }
                        else {
                            val rLow = Range(r.xLo, value - 1, r.mLo, r.mHi, r.aLo, r.aHi, r.sLo, r.sHi)
                            val rHigh = Range(value, r.xHi, r.mLo, r.mHi, r.aLo, r.aHi, r.sLo, r.sHi)
                            rangeQueue.add(Pair(aux[1], rLow))
                            r = rHigh
                        }
                    }
                }
                else if (input == 'm') {
                    if (ruleOp == '>') {
                        if (!r.inRangeM(value)) {
                            if (r.mLo > value) {
                                rangeQueue.add(Pair(aux[1], Range(r.xLo, r.xHi, r.mLo, r.mHi, r.aLo, r.aHi, r.sLo, r.sHi)))
                                break
                            }
                        }
                        else {
                            val rLow = Range(r.xLo, r.xHi, r.mLo, value, r.aLo, r.aHi, r.sLo, r.sHi)
                            val rHigh = Range(r.xLo, r.xHi, value + 1, r.mHi, r.aLo, r.aHi, r.sLo, r.sHi)
                            rangeQueue.add(Pair(aux[1], rHigh))
                            r = rLow
                        }
                    }
                    else {
                        if (!r.inRangeM(value)) {
                            if (r.mHi < value) {
                                rangeQueue.add(Pair(aux[1], Range(r.xLo, r.xHi, r.mLo, r.mHi, r.aLo, r.aHi, r.sLo, r.sHi)))
                                break
                            }
                        }
                        else {
                            val rLow = Range(r.xLo, r.xHi, r.mLo, value - 1, r.aLo, r.aHi, r.sLo, r.sHi)
                            val rHigh = Range(r.xLo, r.xHi, value, r.mHi, r.aLo, r.aHi, r.sLo, r.sHi)
                            rangeQueue.add(Pair(aux[1], rLow))
                            r = rHigh
                        }
                    }
                }
                else if (input == 'a') {
                    if (ruleOp == '>') {
                        if (!r.inRangeA(value)) {
                            if (r.aLo > value) {
                                rangeQueue.add(Pair(aux[1], Range(r.xLo, r.xHi, r.mLo, r.mHi, r.aLo, r.aHi, r.sLo, r.sHi)))
                                break
                            }
                        }
                        else {
                            val rLow = Range(r.xLo, r.xHi, r.mLo, r.mHi, r.aLo, value, r.sLo, r.sHi)
                            val rHigh = Range(r.xLo, r.xHi, r.mLo, r.mHi, value + 1, r.aHi, r.sLo, r.sHi)
                            rangeQueue.add(Pair(aux[1], rHigh))
                            r = rLow
                        }
                    }
                    else {
                        if (!r.inRangeA(value)) {
                            if (r.aHi < value) {
                                rangeQueue.add(Pair(aux[1], Range(r.xLo, r.xHi, r.mLo, r.mHi, r.aLo, r.aHi, r.sLo, r.sHi)))
                                break
                            }
                        }
                        else {
                            val rLow = Range(r.xLo, r.xHi, r.mLo, r.mHi, r.aLo, value - 1, r.sLo, r.sHi)
                            val rHigh = Range(r.xLo, r.xHi, r.mLo, r.mHi, value, r.aHi, r.sLo, r.sHi)
                            rangeQueue.add(Pair(aux[1], rLow))
                            r = rHigh
                        }
                    }
                }
                else {
                    if (ruleOp == '>') {
                        if (!r.inRangeS(value)) {
                            if (r.sLo > value) {
                                rangeQueue.add(Pair(aux[1], Range(r.xLo, r.xHi, r.mLo, r.mHi, r.aLo, r.aHi, r.sLo, r.sHi)))
                                break
                            }
                        }
                        else {
                            val rLow = Range(r.xLo, r.xHi, r.mLo, r.mHi, r.aLo, r.aHi, r.sLo, value)
                            val rHigh = Range(r.xLo, r.xHi, r.mLo, r.mHi, r.aLo, r.aHi, value + 1, r.sHi)
                            rangeQueue.add(Pair(aux[1], rHigh))
                            r = rLow
                        }
                    }
                    else {
                        if (!r.inRangeS(value)) {
                            if (r.sHi < value) {
                                rangeQueue.add(Pair(aux[1], Range(r.xLo, r.xHi, r.mLo, r.mHi, r.aLo, r.aHi, r.sLo, r.sHi)))
                                break
                            }
                        }
                        else {
                            val rLow = Range(r.xLo, r.xHi, r.mLo, r.mHi, r.aLo, r.aHi, r.sLo, value - 1)
                            val rHigh = Range(r.xLo, r.xHi, r.mLo, r.mHi, r.aLo, r.aHi, value, r.sHi)
                            rangeQueue.add(Pair(aux[1], rLow))
                            r = rHigh
                        }
                    }
                }
            }
        }
        return ans
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day19_test")
    check(part1(testInput) == 19114)
    check(part2(testInput) == 167409079868000L)

    val input = readInput("Day19")
    part1(input).println()
    val input2 = readInput("Day19")
    part2(input2).println()
}
