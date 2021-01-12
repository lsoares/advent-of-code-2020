import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths
import java.util.*

// --- Part One ---
data class Point(val x: Int, val y: Int, val z: Int) {
    fun move(move: Triple<Int, Int, Int>) = copy(x + move.first, y + move.second, z + move.third)
}

data class Cubes(val points: List<List<String>>) {

    fun nextAt(point: Point) = when (at(point)) {
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
                    row += nextAt(Point(x - 1, y - 1, z - 1)) ?: INACTIVE
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

    fun at(point: Point) = points
        .getOrNull(point.z)
        ?.getOrNull(point.y)
        ?.getOrNull(point.x)

    fun around(point: Point) = directions.map { at(point.move(it)) }

    fun count(state: Char) = points.sumBy {
        it.sumBy {
            it.count { it == state }
        }
    }

    override fun toString() = points.joinToString(System.lineSeparator().repeat(2)) {
        it.joinToString(System.lineSeparator())
    }

    companion object {
        val directions = (-1..1).flatMap { z ->
            (-1..1).flatMap { y ->
                (-1..1).map { x -> Triple(x, y, z) }
            }
        }.filterNot { it == Triple(0, 0, 0) }

        val ACTIVE = '#'
        val INACTIVE = '.'
    }
}

val sampleInput = listOf(
    listOf(
        ".#.",
        "..#",
        "###",
    )
).let(::Cubes)

val solution1 = (1..6)
    .fold(sampleInput) { acc: Cubes, _ -> acc.iterate() }
    .count(Cubes.ACTIVE)
check(112 == solution1)

val path = "${Paths.get("").toAbsolutePath()}/input/17.txt"
val puzzleInput = Scanner(FileInputStream(File(path))).asSequence().toList().let { Cubes(listOf(it)) }

val puzzleSolution = (1..6)
    .fold(puzzleInput) { acc: Cubes, _ -> acc.iterate() }
    .count(Cubes.ACTIVE)

check(304 == puzzleSolution)