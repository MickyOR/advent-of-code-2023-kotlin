import java.util.*

class Module(val type: Int, val label: String) {
    var state: Int = 0
    var inputModules: MutableList<Pair<String, Int>> = mutableListOf()

    fun processFlipFlop(signalType: Int): Int {
        if (signalType == 1) return -1
        if (state == 0) {
            state = 1
        } else {
            state = 0
        }
        return state
    }

    fun processConjunction(inputLabel: String, signalType: Int): Int {
        for (i in inputModules.indices) {
            if (inputModules[i].first == inputLabel) {
                inputModules[i] = Pair(inputLabel, signalType)
            }
        }
        var result = 0
        for (i in inputModules.indices) {
            if (inputModules[i].second == 0) {
                result = 1
                break
            }
        }
        return result
    }

    fun processBroadcast(): Int {
        return 0
    }

    fun processSignal(inputLabel: String, signalType: Int): Int {
        if (type == 0) {
            return processFlipFlop(signalType)
        } else if (type == 1) {
            return processConjunction(inputLabel, signalType)
        } else {
            return processBroadcast()
        }
    }
}

fun main() {
    fun part1(input: List<String>): Long {
        val graph: MutableMap<String, MutableList<String>> = mutableMapOf()
        val modules: MutableMap<String, Module> = mutableMapOf()
        val invGraph: MutableMap<String, MutableList<String>> = mutableMapOf()
        for (line in input) {
            val parts = line.split(" ")
            var label = ""
            if (parts[0] == "broadcaster") {
                label = "broadcaster"
                modules.put("broadcaster", Module(2, "broadcaster"))
                
            }
            else if (parts[0][0] == '%') {
                label = parts[0].substring(1)
                modules.put(label, Module(0, label))
            }
            else {
                label = parts[0].substring(1)
                modules.put(label, Module(1, label))
            }
            graph.put(label, mutableListOf())
            invGraph.put(label, mutableListOf())
            for (i in 2 until parts.size) {
                var word = parts[i]
                if (word[word.length - 1] == ',') {
                    word = word.substring(0, word.length - 1)
                }
                graph[label]!!.add(word)
            }
        }
        for (line in input) {
            val parts = line.split(" ")
            var label = ""
            if (parts[0] == "broadcaster") {
                label = "broadcaster"
            }
            else if (parts[0][0] == '%') {
                label = parts[0].substring(1)
            }
            else {
                label = parts[0].substring(1)
            }
            for (i in 2 until parts.size) {
                var word = parts[i]
                if (word[word.length - 1] == ',') {
                    word = word.substring(0, word.length - 1)
                }
                invGraph.putIfAbsent(word, mutableListOf())
                invGraph[word]!!.add(label)
            }
        }
        for (key in modules.keys) {
            if (modules[key]!!.type == 1) {
                for (label in invGraph[key]!!) {
                    modules[key]!!.inputModules.add(Pair(label, 0))
                }
            }
        }
        var lowSignals = 0
        var highSignals = 0
        for (ite in 1 .. 1000) {
            val signalQueue: Queue<Pair<Pair<String, String>, Int>> = LinkedList()
            signalQueue.add(Pair(Pair("input", "broadcaster"), 0))
            lowSignals++
            while (signalQueue.isNotEmpty()) {
                val signal = signalQueue.poll()
                val prevLabel = signal.first.first
                val label = signal.first.second
                val signalType = signal.second
                modules.putIfAbsent(label, Module(0, label))
                val result = modules[label]!!.processSignal(prevLabel, signalType)
                if (result == 0 || result == 1) {
                    for (i in graph[label]!!.indices) {
                        signalQueue.add(Pair(Pair(label, graph[label]!![i]), result))
                        if (result == 0) lowSignals++
                        else highSignals++
                    }
                }
            }
        }
        return 1L*lowSignals*highSignals
    }

    fun gcd(a: Long, b: Long): Long {
        if (a == 0L) return b
        return gcd(b%a, a)
    }

    fun part2(input: List<String>): Long {
        val graph: MutableMap<String, MutableList<String>> = mutableMapOf()
        val modules: MutableMap<String, Module> = mutableMapOf()
        val invGraph: MutableMap<String, MutableList<String>> = mutableMapOf()
        for (line in input) {
            val parts = line.split(" ")
            var label = ""
            if (parts[0] == "broadcaster") {
                label = "broadcaster"
                modules.put("broadcaster", Module(2, "broadcaster"))

            }
            else if (parts[0][0] == '%') {
                label = parts[0].substring(1)
                modules.put(label, Module(0, label))
            }
            else {
                label = parts[0].substring(1)
                modules.put(label, Module(1, label))
            }
            graph.put(label, mutableListOf())
            invGraph.put(label, mutableListOf())
            for (i in 2 until parts.size) {
                var word = parts[i]
                if (word[word.length - 1] == ',') {
                    word = word.substring(0, word.length - 1)
                }
                graph[label]!!.add(word)
            }
        }
        for (line in input) {
            val parts = line.split(" ")
            var label = ""
            if (parts[0] == "broadcaster") {
                label = "broadcaster"
            }
            else if (parts[0][0] == '%') {
                label = parts[0].substring(1)
            }
            else {
                label = parts[0].substring(1)
            }
            for (i in 2 until parts.size) {
                var word = parts[i]
                if (word[word.length - 1] == ',') {
                    word = word.substring(0, word.length - 1)
                }
                invGraph.putIfAbsent(word, mutableListOf())
                invGraph[word]!!.add(label)
            }
        }
        for (key in modules.keys) {
            if (modules[key]!!.type == 1) {
                for (label in invGraph[key]!!) {
                    modules[key]!!.inputModules.add(Pair(label, 0))
                }
            }
        }
        val neededGates = mutableSetOf<String>()
        neededGates.add("th")
        neededGates.add("sv")
        neededGates.add("gh")
        neededGates.add("ch")
        var ans = 1L
        var buttonCount = 0
        while (neededGates.isNotEmpty()) {
            buttonCount++
            val signalQueue: Queue<Pair<Pair<String, String>, Int>> = LinkedList()
            signalQueue.add(Pair(Pair("input", "broadcaster"), 0))
            while (signalQueue.isNotEmpty()) {
                val signal = signalQueue.poll()
                val prevLabel = signal.first.first
                val label = signal.first.second
                val signalType = signal.second
                modules.putIfAbsent(label, Module(0, label))
                val result = modules[label]!!.processSignal(prevLabel, signalType)
                if (result == 0 || result == 1) {
                    for (i in graph[label]!!.indices) {
                        signalQueue.add(Pair(Pair(label, graph[label]!![i]), result))
                        if (neededGates.contains(label) && result == 1) {
                            neededGates.remove(label)
                            ans = ans / gcd(ans, 1L*buttonCount) * buttonCount
                        }
                    }
                }
            }
        }
        return ans
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day20_test")
    check(part1(testInput) == 32000000L)
//    check(part2(testInput) == -1)

    val input = readInput("Day20")
    part1(input).println()
    val input2 = readInput("Day20")
    part2(input2).println()
}
