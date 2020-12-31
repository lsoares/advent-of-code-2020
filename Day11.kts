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

check(null == sampleInput1.at(Position(-1, 10)))
check('L' == sampleInput1.at(Position(1, 1)))
check('.' == sampleInput1.at(Position(3, 2)))
check(6 == sampleInput1.around(Position(1, 1)).count { it == Seats.EMPTY })
check(2 == sampleInput1.around(Position(1, 1)).count { it == Seats.FLOOR })
check(37 == sampleInput1.sequence1().last().count(Seats.OCCUPIED))

val path = "${Paths.get("").toAbsolutePath()}/input/11.txt"
val input = Scanner(FileInputStream(File(path))).asSequence().toList().let { Seats(it) }

println(input.sequence1().last().count(Seats.OCCUPIED)) // 2152

// --- Part Two ---
val sampleInput2 = listOf(
    ".......#.",
    "...#.....",
    ".#.......",
    ".........",
    "..#L....#",
    "....#....",
    ".........",
    "#........",
    "...#.....",
).let { Seats(it) }

check('L' == sampleInput2.at(Position(3, 4)))
check(8 == sampleInput2.aroundUntilSeat(Position(3, 4)).count { it == Seats.OCCUPIED })

val sampleInput3 = listOf(
    ".............",
    ".L.L.#.#.#.#.",
    ".............",
).let { Seats(it) }

check('L' == sampleInput3.at(Position(1, 1)))
check(0 == sampleInput3.aroundUntilSeat(Position(1, 1)).count { it == Seats.OCCUPIED })

val sampleInput4 = listOf(
    ".##.##.",
    "#.#.#.#",
    "##...##",
    "...L...",
    "##...##",
    "#.#.#.#",
    ".##.##.",
).let { Seats(it) }

check('L' == sampleInput4.at(Position(3, 3)))
check(0 == sampleInput4.aroundUntilSeat(Position(3, 3)).count { it == Seats.OCCUPIED })

check(26 == sampleInput1.sequence2().last().count(Seats.OCCUPIED))
println(input.sequence2().last().count(Seats.OCCUPIED)) // 1937