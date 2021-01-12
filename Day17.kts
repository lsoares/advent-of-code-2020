import Day17.HyperCubes
import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths
import java.util.*

// --- Part One ---
data class Point3D(val x: Int, val y: Int, val z: Int) {
    fun move(move: Triple<Int, Int, Int>) = copy(x + move.first, y + move.second, z + move.third)
}

data class Cubes(val points: List<List<String>>) {

    fun nextAt(point: Point3D) = when (at(point)) {
        ACTIVE -> when (around(point).count { it == ACTIVE }) {
            2, 3 -> ACTIVE
            else -> null
        }
        else -> when (around(point).count { it == ACTIVE }) {
            3 -> ACTIVE
            else -> null
        }
    }

    fun iterate(): Cubes {
        var z = 0
        val newPoints = mutableListOf<List<String>>()
        while (z < points.size + 2) {
            var y = 0
            val yPlane = mutableListOf<String>()
            while (y < points.first().size + 2) {
                var x = 0
                val row = mutableListOf<Char>()
                while (x < points.first().first().length + 2) {
                    row += nextAt(Point3D(x - 1, y - 1, z - 1)) ?: INACTIVE
                    x++
                }
                yPlane += row.joinToString("")
                y++
            }
            newPoints += yPlane
            z++
        }
        return Cubes(newPoints)
    }

    fun at(point: Point3D) = points
        .getOrNull(point.z)
        ?.getOrNull(point.y)
        ?.getOrNull(point.x)

    fun around(point: Point3D) = directions.map { at(point.move(it)) }

    fun count(state: Char) = points.sumBy {
        it.sumBy {
            it.count { it == state }
        }
    }

    override fun toString() = points.joinToString(System.lineSeparator().repeat(2)) {
        it.joinToString(System.lineSeparator())
    }

    companion object {
        val directions =
            (-1..1).flatMap { z ->
                (-1..1).flatMap { y ->
                    (-1..1).map { x -> Triple(x, y, z) }
                }
            }.filterNot { it == Triple(0, 0, 0) }

        val ACTIVE = '#'
        val INACTIVE = '.'
    }
}

val sampleInput = listOf(
    ".#.",
    "..#",
    "###",
)

val solution1 = (1..6)
    .fold(Cubes(listOf(sampleInput))) { cubes, _ -> cubes.iterate() }
    .count(Cubes.ACTIVE)
check(112 == solution1)

fun getPuzzleInput() = Scanner(FileInputStream(File("${Paths.get("").toAbsolutePath()}/input/17.txt")))
    .asSequence().toList()

val puzzleSolution = (1..6)
    .fold(Cubes(listOf(getPuzzleInput()))) { cubes, _ -> cubes.iterate() }
    .count(Cubes.ACTIVE)

check(304 == puzzleSolution)

// --- Part Two ---
data class Point4D(val x: Int, val y: Int, val z: Int, val w: Int) {
    fun move(move: Move4D) = copy(x + move.first, y + move.second, z + move.third, w + move.fourth)
}

data class Move4D(val first: Int, val second: Int, val third: Int, val fourth: Int)

data class HyperCubes(val points: List<List<List<String>>>) {

    fun nextAt(point: Point4D) = when (at(point)) {
        ACTIVE -> when (around(point).count { it == ACTIVE }) {
            2, 3 -> ACTIVE
            else -> null
        }
        else -> when (around(point).count { it == ACTIVE }) {
            3 -> ACTIVE
            else -> null
        }
    }

    fun iterate(): HyperCubes {
        val newPoints = mutableListOf<List<List<String>>>()
        var w = 0
        while (w < points.size + 2) {
            val wHyperPlane = mutableListOf<List<String>>()
            var z = 0
            while (z < points.first().size + 2) {
                val yPlane = mutableListOf<String>()
                var y = 0
                while (y < points.first().first().size + 2) {
                    val row = mutableListOf<Char>()
                    var x = 0
                    while (x < points.first().first().first().length + 2) {
                        row += nextAt(Point4D(x - 1, y - 1, z - 1, w - 1)) ?: INACTIVE
                        x++
                    }
                    yPlane += row.joinToString("")
                    y++
                }
                wHyperPlane += yPlane
                z++
            }
            newPoints += wHyperPlane
            w++
        }

        return HyperCubes(newPoints)
    }

    fun at(point: Point4D) = points
        .getOrNull(point.w)
        ?.getOrNull(point.z)
        ?.getOrNull(point.y)
        ?.getOrNull(point.x)

    fun around(point: Point4D) = directions.map { at(point.move(it)) }

    fun count(state: Char) = points.sumBy {
        it.sumBy {
            it.sumBy {
                it.count { it == state }
            }
        }
    }

    companion object {
        val directions =
            (-1..1).flatMap { w ->
                (-1..1).flatMap { z ->
                    (-1..1).flatMap { y ->
                        (-1..1).map { x -> Move4D(x, y, z, w) }
                    }
                }
            }.filterNot { it == Move4D(0, 0, 0, 0) }

        val ACTIVE = '#'
        val INACTIVE = '.'
    }
}

val solution2 = (1..6)
    .fold(HyperCubes(listOf(listOf(sampleInput)))) { hyperCubes, _ -> hyperCubes.iterate() }
    .count(HyperCubes.ACTIVE)
check(848 == solution2)

val puzzleSolution2 = (1..6)
    .fold(HyperCubes(listOf(listOf(getPuzzleInput())))) { hyperCubes, _ -> hyperCubes.iterate() }
    .count(Cubes.ACTIVE)

check(1868 == puzzleSolution2)