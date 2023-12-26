import java.util.*

data class FlowEdge(var to: Int, var rev: Int, var f: Int, var cap: Int)

class Dinitz {
    var S = 0
    var T = 0
    var nodes = 0
    var g = mutableListOf<MutableList<FlowEdge>>()
    var q = intArrayOf()
    var work = intArrayOf()
    var lvl = intArrayOf()

    fun init(n: Int, s: Int, t: Int) {
        nodes = n
        S = s
        T = t
        g = MutableList(nodes) { mutableListOf() }
        q = IntArray(nodes)
    }

    fun addEdge(st: Int, en: Int, cap: Int) {
        val a = FlowEdge(en, g[en].size, 0, cap)
        val b = FlowEdge(st, g[st].size, 0, 0)
        g[st].add(a)
        g[en].add(b)
    }

    fun bfs(): Boolean {
        var qt = 0
        q[qt] = S
        qt++
        lvl = IntArray(nodes) {-1}
        lvl[S] = 0
        var qh = -1
        while (true) {
            qh++
            if (qh >= qt) break
            val v = q[qh]
            for (e in g[v]) {
                val u = e.to
                if (e.cap <= e.f || lvl[u] != -1) continue
                lvl[u] = lvl[v] + 1
                q[qt] = u
                qt++
            }
        }
        return lvl[T] != -1
    }

    fun dfs(v: Int, f: Int): Int {
        if (v == T || f == 0) return f
        while (work[v] < g[v].size) {
            val i = work[v]
            work[v]++
            if (g[v][i].cap <= g[v][i].f || lvl[g[v][i].to] != lvl[v] + 1) continue
            val df = dfs(g[v][i].to, minOf(f, g[v][i].cap - g[v][i].f))
            if (df > 0) {
                g[v][i].f += df
                g[g[v][i].to][g[v][i].rev].f -= df
                return df;
            }
        }
        return 0
    }

    fun maxFlow(): Int {
        var flow = 0
        while (bfs()) {
            work = IntArray(nodes) {0}
            while (true) {
                val df = dfs(S, Int.MAX_VALUE/4)
                if (df == 0) break
                flow += df
            }
        }
        return flow
    }

    fun cutCompsSizes(): Pair<Int, Int> {
        var fst = 0
        val q: Queue<Int> = LinkedList()
        val vis = BooleanArray(nodes) {false}
        fst++
        q.add(S)
        vis[S] = true
        while (q.isNotEmpty()) {
            val u = q.poll()
            for (e in g[u]) {
                val v = e.to
                if (e.cap <= e.f) continue
                if (vis[v]) continue
                fst++
                vis[v] = true
                q.add(v)
            }
        }
        return Pair(fst, nodes-fst)
    }
}

fun main() {

    fun part1(input: List<String>): Int {
        var curNode = 0
        val id = mutableMapOf<String, Int>()
        val g = mutableMapOf<Int, MutableList<Int>>()
        for (line in input) {
            val words = line.split(" ")
            val u = words[0].dropLast(1)
            if (!id.containsKey(u)) {
                id[u] = curNode
                curNode++
            }
            if (!g.containsKey(id[u])) g.put(id[u]!!, mutableListOf())
            for (i in 1 until words.size) {
                val v = words[i]
                if (!id.containsKey(v)) {
                    id[v] = curNode
                    curNode++
                }
                if (!g.containsKey(id[v])) g.put(id[v]!!, mutableListOf())
                g[id[u]!!]!!.add(id[v]!!)
                g[id[v]!!]!!.add(id[u]!!)
            }
        }
        for (i in 0 until curNode) {
            for (j in i+1 until curNode) {
                val dinitz = Dinitz()
                val S = curNode
                val T = curNode+1
                dinitz.init(curNode+2, S, T)
                for (u in 0 until curNode) {
                    for (v in g[u]!!) {
                        dinitz.addEdge(u, v, 1)
                    }
                }
                dinitz.addEdge(S, i, Int.MAX_VALUE/4)
                dinitz.addEdge(j, T, Int.MAX_VALUE/4)
                val flow = dinitz.maxFlow()
                if (flow < 3) {
                    println("We have cables to spare!")
                }
                if (flow > 3) continue
                val (a, b) = dinitz.cutCompsSizes()
                return (a-1) * (b-1)
            }
        }
        return -1
    }

    fun part2(input: List<String>): Int {
        return -1
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day25_test")
    check(part1(testInput) == 54)
    check(part2(testInput) == -1)

    val input = readInput("Day25")
    part1(input).println()
//    val input2 = readInput("Day2X_2")
//    part2(input2).println()
}
