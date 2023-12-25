

import java.util.*
import kotlin.math.*

data class Brick(var x1: Int, var y1: Int, var z1: Int, var x2: Int, var y2: Int, var z2: Int) {

}

fun main() {
    fun part1(input: List<String>): Int {
        val bricks = mutableListOf<Brick>()
        val grid = Array(10) { Array(10) { BooleanArray(290) { false } } }
        for (line in input) {
            val aux = line.split("~")
            var (x1, y1, z1) = aux[0].split(",").map { it.toInt() }
            var (x2, y2, z2) = aux[1].split(",").map { it.toInt() }
            if (z1 > z2) {
                val aux = z1
                z1 = z2
                z2 = aux
            }
            if (y1 > y2) {
                val aux = y1
                y1 = y2
                y2 = aux
            }
            if (x1 > x2) {
                val aux = x1
                x1 = x2
                x2 = aux
            }
            val brick = Brick(x1, y1, z1, x2, y2, z2)
            bricks.add(brick)
            for (x in x1..x2) {
                for (y in y1..y2) {
                    for (z in z1..z2) {
                        grid[x][y][z] = true
                    }
                }
            }
        }
        for (x in 0..9) {
            for (y in 0..9) {
                grid[x][y][0] = true
            }
        }
        while (true) {
            bricks.sortBy { minOf(it.z1, it.z2) }
            var change = false
            for (i in bricks.indices) {
                var canMove = true
                while (canMove) {
                    val (x1, y1, z1, x2, y2, z2) = bricks[i]
                    for (x in x1..x2) {
                        for (y in y1..y2) {
                            if (grid[x][y][z1-1]) canMove = false
                            if (!canMove) break
                        }
                        if (!canMove) break
                    }
                    if (canMove) {
                        change = true
                        for (x in x1..x2) {
                            for (y in y1..y2) {
                                for (z in z1..z2) {
                                    grid[x][y][z] = false
                                }
                            }
                        }
                        bricks[i].z1--
                        bricks[i].z2--
                        for (x in x1..x2) {
                            for (y in y1..y2) {
                                for (z in bricks[i].z1..bricks[i].z2) {
                                    grid[x][y][z] = true
                                }
                            }
                        }
                    }
                }
            }
            if (!change) break
        }
        val mark = Array(10) { Array(10) { IntArray(290) { -1 } } }
        for (i in bricks.indices) {
            val (x1, y1, z1, x2, y2, z2) = bricks[i]
            for (x in x1..x2) {
                for (y in y1..y2) {
                    for (z in z1..z2) {
                        mark[x][y][z] = i
                    }
                }
            }
        }
        val n = bricks.size
        val inDeg = IntArray(n) { 0 }
        val adj = Array(n) { mutableListOf<Int>() }
        for (i in bricks.indices) {
            val (x1, y1, z1, x2, y2, z2) = bricks[i]
            val adjacent = mutableSetOf<Int>()
            for (x in x1..x2) {
                for (y in y1..y2) {
                    if (grid[x][y][z2 + 1]) {
                        adjacent.add(mark[x][y][z2 + 1])
                    }
                }
            }
            for (j in adjacent) {
                inDeg[j]++
                adj[i].add(j)
            }
        }
        var ans = 0
        for (i in bricks.indices) {
            ans++
            for (j in adj[i]) {
                if (inDeg[j] == 1) {
                    ans--
                    break
                }
            }
        }
        return ans
    }

    fun part2(input: List<String>): Int {
        val bricks = mutableListOf<Brick>()
        val grid = Array(10) { Array(10) { BooleanArray(290) { false } } }
        for (line in input) {
            val aux = line.split("~")
            var (x1, y1, z1) = aux[0].split(",").map { it.toInt() }
            var (x2, y2, z2) = aux[1].split(",").map { it.toInt() }
            if (z1 > z2) {
                val aux = z1
                z1 = z2
                z2 = aux
            }
            if (y1 > y2) {
                val aux = y1
                y1 = y2
                y2 = aux
            }
            if (x1 > x2) {
                val aux = x1
                x1 = x2
                x2 = aux
            }
            val brick = Brick(x1, y1, z1, x2, y2, z2)
            bricks.add(brick)
            for (x in x1..x2) {
                for (y in y1..y2) {
                    for (z in z1..z2) {
                        grid[x][y][z] = true
                    }
                }
            }
        }
        for (x in 0..9) {
            for (y in 0..9) {
                grid[x][y][0] = true
            }
        }
        while (true) {
            bricks.sortBy { minOf(it.z1, it.z2) }
            var change = false
            for (i in bricks.indices) {
                var canMove = true
                while (canMove) {
                    val (x1, y1, z1, x2, y2, z2) = bricks[i]
                    for (x in x1..x2) {
                        for (y in y1..y2) {
                            if (grid[x][y][z1-1]) canMove = false
                            if (!canMove) break
                        }
                        if (!canMove) break
                    }
                    if (canMove) {
                        change = true
                        for (x in x1..x2) {
                            for (y in y1..y2) {
                                for (z in z1..z2) {
                                    grid[x][y][z] = false
                                }
                            }
                        }
                        bricks[i].z1--
                        bricks[i].z2--
                        for (x in x1..x2) {
                            for (y in y1..y2) {
                                for (z in bricks[i].z1..bricks[i].z2) {
                                    grid[x][y][z] = true
                                }
                            }
                        }
                    }
                }
            }
            if (!change) break
        }
        val mark = Array(10) { Array(10) { IntArray(290) { -1 } } }
        for (i in bricks.indices) {
            val (x1, y1, z1, x2, y2, z2) = bricks[i]
            for (x in x1..x2) {
                for (y in y1..y2) {
                    for (z in z1..z2) {
                        mark[x][y][z] = i
                    }
                }
            }
        }
        val n = bricks.size
        val inDeg = IntArray(n) { 0 }
        val adj = Array(n) { mutableListOf<Int>() }
        for (i in bricks.indices) {
            val (x1, y1, z1, x2, y2, z2) = bricks[i]
            val adjacent = mutableSetOf<Int>()
            for (x in x1..x2) {
                for (y in y1..y2) {
                    if (grid[x][y][z2 + 1]) {
                        adjacent.add(mark[x][y][z2 + 1])
                    }
                }
            }
            for (j in adjacent) {
                inDeg[j]++
                adj[i].add(j)
            }
        }
        var ans = 0
        for (i in bricks.indices) {
            val tmpDeg = inDeg.copyOf()
            val q: Queue<Int> = LinkedList()
            q.add(i)
            var cnt = -1
            while (q.isNotEmpty()) {
                val u = q.poll()
                cnt++
                for (v in adj[u]) {
                    tmpDeg[v]--
                    if (tmpDeg[v] == 0) {
                        q.add(v)
                    }
                }
            }
            ans += cnt
        }
        return ans
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day22_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 7)

    val input = readInput("Day22")
    part1(input).println()
    val input2 = readInput("Day22")
    part2(input2).println()
}
