import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths
import java.util.*

// --- Part One ---
val sampleInput = listOf(
    "L.LL.LL.LL",
    "LLLLLLL.LL",
    "L.L.L..L..",
    "LLLL.LL.LL",
    "L.LL.LL.LL",
    "L.LLLLL.LL",
    "..L.L.....",
    "LLLLLLLLLL",
    "L.LLLLLL.L",
    "L.LLLLL.LL",
).let { Seats(it) }

data class Seats(val rows: List<String>) {
    data class Position(val x: Int, val y: Int) {
        fun move(move: Pair<Int, Int>) = Position(x + move.first, y + move.second)
    }

    fun iterate() = Seats(rows.mapIndexed { y, row ->
        row.mapIndexed { x, _ -> iterate(Position(x, y)) }.joinToString("")
    })

    private fun iterate(position: Position) = when {
        at(position) == EMPTY && around(position).none { it == OCCUPIED } -> OCCUPIED
        at(position) == OCCUPIED && around(position).count { it == OCCUPIED } >= 4 -> EMPTY
        else -> at(position)
    }

    fun around(pos: Position) =
        setOf(-1 to -1, 0 to -1, 1 to -1, -1 to 0, 1 to 0, -1 to 1, 0 to 1, 1 to 1)
            .map { at(pos.move(it)) }

    fun count(status: Char) = rows.joinToString("").count { it == status }

    fun at(position: Position) =
        rows.getOrNull(position.y)?.getOrNull(position.x)

    override fun toString() = rows.joinToString("\n")

    companion object {
        val OCCUPIED = '#'
        val EMPTY = 'L'
    }
}

tailrec fun Seats.iterateUntilStable(): Seats {
    val iteration = iterate()
    if (this == iteration) return this

    return iteration.iterateUntilStable()
}

check(37 == sampleInput.iterateUntilStable().count(Seats.OCCUPIED))

val path = "${Paths.get("").toAbsolutePath()}/input/11.txt"
val input = Scanner(FileInputStream(File(path))).asSequence().toList().let { Seats(it) }

println(input.iterateUntilStable().count(Seats.OCCUPIED)) // 2152
