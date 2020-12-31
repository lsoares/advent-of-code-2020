import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths
import java.util.*

// --- Part One ---
val sampleInput1 = listOf(
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

data class Position(val x: Int, val y: Int) {
    fun move(move: Pair<Int, Int>) = Position(x + move.first, y + move.second)
}

check(37 == sampleInput1.sequence1().last().count(Seats.OCCUPIED))

data class Seats(val rows: List<String>) {

    fun sequence1() = generateSequence(this) { before ->
        before.rows
            .mapIndexed { y, row ->
                row.mapIndexed { x, _ -> before.iterate1(Position(x, y)) }.joinToString("")
            }
            .let { Seats(it) }
            .takeIf { it != before }
    }

    private fun iterate1(position: Position) = when {
        at(position) == EMPTY && around(position).none { it == OCCUPIED } -> OCCUPIED
        at(position) == OCCUPIED && around(position).count { it == OCCUPIED } >= 4 -> EMPTY
        else -> at(position)
    }

    fun sequence2() = generateSequence(this) { before ->
        before.rows
            .mapIndexed { y, row ->
                row.mapIndexed { x, _ -> before.iterate2(Position(x, y)) }.joinToString("")
            }
            .let { Seats(it) }
            .takeIf { it != before }
    }

    private fun iterate2(position: Position) = when {
        at(position) == EMPTY && aroundUntilSeat(position).none { it == OCCUPIED } -> OCCUPIED
        at(position) == OCCUPIED && aroundUntilSeat(position).count { it == OCCUPIED } >= 5 -> EMPTY
        else -> at(position)
    }

    fun around(pos: Position) = directions.mapNotNull { at(pos.move(it)) }

    fun aroundUntilSeat(pos: Position) = directions.mapNotNull { visibleSeatFrom(pos, it) }

    private fun visibleSeatFrom(currentPos: Position, move: Pair<Int, Int>): Char? =
        currentPos.move(move).let { next ->
            when (at(next)) {
                FLOOR -> visibleSeatFrom(next, move)
                else -> at(next)
            }
        }

    fun at(position: Position) =
        rows.getOrNull(position.y)?.getOrNull(position.x)

    fun count(status: Char) = rows.joinToString("").count { it == status }

    override fun toString() = rows.joinToString("\n")

    companion object {
        private val directions = listOf(0 to -1, 1 to -1, 1 to 0, 1 to 1, 0 to 1, -1 to 1, -1 to 0, -1 to -1)
        val OCCUPIED = '#'
        val EMPTY = 'L'
        val FLOOR = '.'
    }
}

val path = "${Paths.get("").toAbsolutePath()}/input/11.txt"
val input = Scanner(FileInputStream(File(path))).asSequence().toList().let { Seats(it) }

check(2152 == input.sequence1().last().count(Seats.OCCUPIED))

// --- Part Two ---
check(26 == sampleInput1.sequence2().last().count(Seats.OCCUPIED))
check(1937 == input.sequence2().last().count(Seats.OCCUPIED))