import kotlin.math.*
import java.math.BigDecimal
import com.microsoft.z3.*

data class Stone(var x: Double, var y: Double, var z: Double, var vx: Double, var vy: Double, var vz: Double) {

}

val EPS = BigDecimal.valueOf(1e-9)
val INF = 2
var ans = mutableListOf<BigDecimal>()

fun gauss(a: MutableList<MutableList<BigDecimal>>): Int {
    val n = a.size
    val m = a[0].size - 1

    val where = IntArray(m) {-1}
    var col = -1
    var row = 0
    while (col < m && row < n) {
        col++
        if (col == m) break
        var sel = row
        for (i in row until n) {
            if (a[i][col].abs() > a[sel][col].abs()) sel = i
        }
        if (a[sel][col].abs() <= EPS) continue
        for (i in col..m) {
            val tmp = a[row][i]
            a[row][i] = a[sel][i]
            a[sel][i] = tmp
        }
        where[col] = row
        for (i in 0 until n) {
            if (i != row) {
                val c = a[i][col] / a[row][col]
                for (j in col..m) {
                    a[i][j] -= a[row][j] * c
                }
            }
        }
        row++
    }
    ans = MutableList<BigDecimal>(m) {BigDecimal.ZERO}
    for (i in 0 until m) {
        if (where[i] != -1) {
            ans[i] = a[where[i]][m] / a[where[i]][i]
        }
    }
    for (i in 0 until n) {
        var sum = BigDecimal.ZERO
        for (j in 0 until m) {
            sum += ans[j] * a[i][j]
        }
        if (sum - a[i][m].abs() > EPS) {
            return 0
        }
    }
    for (i in 0 until m) if (where[i] == -1) return INF
    return 1
}

fun lineIntersection(line1: MutableList<Pair<BigDecimal, BigDecimal>>, line2: MutableList<Pair<BigDecimal, BigDecimal>>): Pair<BigDecimal, BigDecimal> {
    val xdiff = arrayOf(line1[0].first - line1[1].first, line2[0].first - line2[1].first)
    val ydiff = arrayOf(line1[0].second - line1[1].second, line2[0].second - line2[1].second)

    val det: (Array<BigDecimal>, Array<BigDecimal>) -> BigDecimal = {
        a, b -> a[0]*b[1] - a[1]*b[0]
    }

    val div = det(xdiff, ydiff)
    if (div.equals(BigDecimal.ZERO)) return Pair(BigDecimal(-1), BigDecimal(-1))
    val d = arrayOf(
        det(arrayOf(line1[0].first, line1[0].second), arrayOf(line1[1].first, line1[1].second)),
        det(arrayOf(line2[0].first, line2[0].second), arrayOf(line2[1].first, line2[1].second))
    )
    val x = det(d, xdiff) / div
    val y = det(d, ydiff) / div
    return Pair(x, y)
}

fun intersectSegments1(stone1: Stone, stone2: Stone): Triple<BigDecimal, BigDecimal, BigDecimal>? {
//    val eqSystem = mutableListOf<MutableList<Double>>()
//    eqSystem.add(mutableListOf(stone1.vx, 0.0, -1.0, 0.0, -stone1.x))
//    eqSystem.add(mutableListOf(stone1.vy, 0.0, 0.0, -1.0, -stone1.y))
//    eqSystem.add(mutableListOf(0.0, stone2.vx, -1.0, 0.0, -stone2.x))
//    eqSystem.add(mutableListOf(0.0, stone2.vy, 0.0, -1.0, -stone2.y))
//    val bigSystem = mutableListOf<MutableList<BigDecimal>>()
//    for (eq in eqSystem) {
//        val sys = mutableListOf<BigDecimal>()
//        for (value in eq) {
//            sys.add(BigDecimal.valueOf(value))
//        }
//        bigSystem.add(sys)
//    }
//    val solutions = gauss(bigSystem)
//    if (solutions != 1) return null
//    if (ans[0] < BigDecimal.ZERO || ans[1] < BigDecimal.ZERO) return null
//    return Triple(ans[2], ans[3], BigDecimal.ZERO)
    val line1 = mutableListOf<Pair<BigDecimal, BigDecimal>>(Pair(stone1.x.toBigDecimal(), stone1.y.toBigDecimal()), Pair((stone1.x+stone1.vx).toBigDecimal(), (stone1.y+stone1.vy).toBigDecimal()))
    val line2 = mutableListOf<Pair<BigDecimal, BigDecimal>>(Pair(stone2.x.toBigDecimal(), stone2.y.toBigDecimal()), Pair((stone2.x+stone2.vx).toBigDecimal(), (stone2.y+stone2.vy).toBigDecimal()))
    val inter = lineIntersection(line1, line2)
    if (inter.first == BigDecimal(-1)) return null

    if ( (inter.first  - stone1.x.toBigDecimal() < 0.toBigDecimal()) != (stone1.vx < 0) ) return null
    if ( (inter.second - stone1.y.toBigDecimal() < 0.toBigDecimal()) != (stone1.vy < 0) ) return null
    if ( (inter.first  - stone2.x.toBigDecimal() < 0.toBigDecimal()) != (stone2.vx < 0) ) return null
    if ( (inter.second - stone2.y.toBigDecimal() < 0.toBigDecimal()) != (stone2.vy < 0) ) return null

    return Triple(inter.first, inter.second, BigDecimal(0))
}

fun main() {
    fun part1(input: List<String>): Int {
        val stones = mutableListOf<Stone>()
        for (line in input) {
            val aux = line.split(" @ ")
            val (x, y, z)    = aux[0].split(", ").map { it.toDouble() }
            val (vx, vy, vz) = aux[1].split(", ").map { it.toDouble() }
            val stone = Stone(x, y, 0.0, vx, vy, 0.0)
            stones.add(stone)
        }
        var ans = 0
        val lo = BigDecimal.valueOf(200000000000000.0)
        val hi = BigDecimal.valueOf(400000000000000.0)
        for (i in stones.indices) {
            for (j in i+1 until stones.size) {
                val col = intersectSegments1(stones[i], stones[j])
                if (col != null &&
                    lo <= col.first && col.first <= hi &&
                    lo <= col.second && col.second <= hi) {
                    ans++
                }
            }
        }
        return ans
    }

    fun part2(input: List<String>): Int {
        val stones = mutableListOf<Stone>()
        for (line in input) {
            val aux = line.split(" @ ")
            val (x, y, z)    = aux[0].split(", ").map { it.toDouble() }
            val (vx, vy, vz) = aux[1].split(", ").map { it.toDouble() }
            val stone = Stone(x, y, 0.0, vx, vy, 0.0)
            stones.add(stone)
        }
        var ans = 0

        var ctx = Context()
        var solver = ctx.mkSolver()

        var x = ctx.mkIntConst("x")
        var y = ctx.mkIntConst("y")
        var z = ctx.mkIntConst("z")

        var vx = ctx.mkIntConst("vx")
        var vy = ctx.mkIntConst("vy")
        var vz = ctx.mkIntConst("vz")

        for (i in 0..2) {
            val ti = ctx.mkIntConst("t$i")
            var px = ctx.mkInt(stones[i].x.toInt())
            var py = ctx.mkInt(stones[i].y.toInt())
            var pz = ctx.mkInt(stones[i].z.toInt())
            var pvx = ctx.mkInt(stones[i].vx.toInt())
            var pvy = ctx.mkInt(stones[i].vy.toInt())
            var pvz = ctx.mkInt(stones[i].vz.toInt())
            solver.add(ctx.mkBool(ti >= ctx.mkInt(0)))
            solver.add( ctx.mkEq( ctx.mkAdd(x, ctx.mkMul(ti, vx)), ctx.mkAdd(px, ctx.mkMul(ti, pvx)) ) )
            solver.add( ctx.mkEq( ctx.mkAdd(y, ctx.mkMul(ti, vy)), ctx.mkAdd(py, ctx.mkMul(ti, pvy)) ) )
            solver.add( ctx.mkEq( ctx.mkAdd(z, ctx.mkMul(ti, vz)), ctx.mkAdd(pz, ctx.mkMul(ti, pvz)) ) )
        }
        solver.check()
        val model = solver.model
        val rx = model.eval(x, true)
        val ry = model.eval(y, true)
        val rz = model.eval(z, true)
        return rx.toString().toInt() + ry.toString().toInt() + rz.toString().toInt()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day24_test")
//    check(part1(testInput) == 2)
    check(part2(testInput) == 47)

    val input = readInput("Day24")
    part1(input).println()
    val input2 = readInput("Day24")
    part2(input2).println()
}
