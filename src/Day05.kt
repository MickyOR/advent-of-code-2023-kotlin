import kotlin.math.max
import kotlin.math.min

fun main() {
    fun part1(input: List<String>): Long {
        var ans = Long.MAX_VALUE
        var seedsWords = input[0].split(" ")
        var currentSeeds = mutableSetOf<Long>()
        for (i in 1..seedsWords.size-1) {
            currentSeeds.add(seedsWords[i].toLong())
        }
        var newSeeds = mutableSetOf<Long>()
        for (i in 2..input.size-1) {
            val line = input[i]
            if (line == "") {
                for (seed in newSeeds) {
                    currentSeeds.add(seed);
                }
                newSeeds.clear()
                continue
            }
            if (line.last() == ':') continue
            val range = line.split(" ")
            var des = range[0].toLong()
            var ori = range[1].toLong()
            var len = range[2].toLong()
            val toDel = mutableListOf<Long>()
            for (seed in currentSeeds) {
                if (ori <= seed && seed < ori + len) {
                    toDel.add(seed)
                }
            }
            for (mappedSeed in toDel) {
                currentSeeds.remove(mappedSeed)
                newSeeds.add(des + mappedSeed - ori)
            }
        }
        for (seed in newSeeds) currentSeeds.add(seed)
        return currentSeeds.min()
    }

    fun part2(input: List<String>): Long {
        var seedsWords = input[0].split(" ")
        var currentSeeds = mutableSetOf<Pair<Long, Long>>()
        for (i in 1..seedsWords.size-1 step 2) {
            currentSeeds.add(Pair(seedsWords[i].toLong(), seedsWords[i].toLong()+seedsWords[i+1].toLong()-1))
        }
        var newSeeds = mutableSetOf<Pair<Long, Long>>()
        for (i in 2..input.size-1) {
            val line = input[i]
            if (line == "") {
                for (seed in newSeeds) {
                    currentSeeds.add(seed);
                }
                newSeeds.clear()
                continue
            }
            if (line.last() == ':') continue
            val range = line.split(" ")
            var des = range[0].toLong()
            var ori = range[1].toLong()
            var len = range[2].toLong()
            while (true) {
                val toAddNew = mutableListOf<Pair<Long, Long>>()
                val toAddOld = mutableListOf<Pair<Long, Long>>()
                val toDel = mutableListOf<Pair<Long, Long>>()
                for (seed in currentSeeds) {
                    if (max(seed.first, ori) <= min(seed.second, ori+len-1)) {
                        if (ori <= seed.first && seed.second <= ori+len-1) {
                            toAddNew.add(Pair(des+seed.first-ori, des+seed.second-ori))
                            toDel.add(seed)
                        }
                        else if (seed.first < ori) {
                            toDel.add(seed)
                            toAddOld.add(Pair(seed.first, ori-1))
                            toAddNew.add(Pair(des, des+seed.second-ori))
                        }
                        else if (ori+len-1 < seed.second) {
                            toDel.add(seed)
                            toAddOld.add(Pair(ori+len, seed.second))
                            toAddNew.add(Pair(des+seed.first-ori, des+ori+len-1-ori))
                        }
                        else {
                            assert(false)
                        }
                        break
                    }
                }
                var change = false
                for (seed in toDel) {
                    change = true
                    currentSeeds.remove(seed)
                }
                for (seed in toAddOld) {
                    change = true
                    currentSeeds.add(seed)
                }
                for (seed in toAddNew) {
                    change = true
                    newSeeds.add(seed)
                }
                if (!change) break
            }
        }
        for (seed in newSeeds) currentSeeds.add(seed)
        var minLocation = Long.MAX_VALUE
        for (seed in currentSeeds) {
            minLocation = min(minLocation, seed.first)
        }
        return minLocation
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35L)
    check(part2(testInput) == 46L)

    val input = readInput("Day05")
    part1(input).println()
    val input2 = readInput("Day05")
    part2(input2).println()
}
