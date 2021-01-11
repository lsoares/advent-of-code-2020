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
).let(::Seats)

data class Seat(val x: Int, val y: Int) {
    fun move(move: Pair<Int, Int>) = copy(x + move.first, y + move.second)
}

check(37 == sampleInput.sequence1().last().count(Seats.OCCUPIED))

data class Seats(val rows: List<String>) {

    fun sequence1() = generateSequence(this) { before ->
        before.rows
            .mapIndexed { y, row ->
                row.mapIndexed { x, _ -> before.iterate1(Seat(x, y)) }.joinToString("")
            }
            .let(::Seats)
            .takeIf { it != before }
    }

    private fun iterate1(seat: Seat) = when {
        at(seat) == EMPTY && around(seat).none { it == OCCUPIED } -> OCCUPIED
        at(seat) == OCCUPIED && around(seat).count { it == OCCUPIED } >= 4 -> EMPTY
        else -> at(seat)
    }

    private fun around(seat: Seat) = directions.mapNotNull { at(seat.move(it)) }

    fun sequence2() = generateSequence(this) { before ->
        before.rows
            .mapIndexed { y, row ->
                row.mapIndexed { x, _ -> before.iterate2(Seat(x, y)) }.joinToString("")
            }
            .let(::Seats)
            .takeIf { it != before }
    }

    private fun iterate2(seat: Seat) = when {
        at(seat) == EMPTY && aroundUntilSeat(seat).none { it == OCCUPIED } -> OCCUPIED
        at(seat) == OCCUPIED && aroundUntilSeat(seat).count { it == OCCUPIED } >= 5 -> EMPTY
        else -> at(seat)
    }

    private fun aroundUntilSeat(seat: Seat) = directions.mapNotNull { visibleSeatFrom(seat, it) }

    fun count(status: Char) = rows.joinToString("").count { it == status }

    private fun visibleSeatFrom(currentSeat: Seat, move: Pair<Int, Int>): Char? =
        currentSeat.move(move).let { next ->
            when (at(next)) {
                FLOOR -> visibleSeatFrom(next, move)
                else -> at(next)
            }
        }

    override fun toString() = rows.joinToString("\n")

    private fun at(seat: Seat) =
        rows.getOrNull(seat.y)?.getOrNull(seat.x)

    companion object {
        private val directions = listOf(0 to -1, 1 to -1, 1 to 0, 1 to 1, 0 to 1, -1 to 1, -1 to 0, -1 to -1)
        val OCCUPIED = '#'
        val EMPTY = 'L'
        val FLOOR = '.'
    }
}

val path = "${Paths.get("").toAbsolutePath()}/input/11.txt"
val puzzleInput = Scanner(FileInputStream(File(path))).asSequence().toList().let { Seats(it) }

check(2152 == puzzleInput.sequence1().last().count(Seats.OCCUPIED))

// --- Part Two ---
check(26 == sampleInput.sequence2().last().count(Seats.OCCUPIED))
check(1937 == puzzleInput.sequence2().last().count(Seats.OCCUPIED))
