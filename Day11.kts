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

data class Position(val x: Int, val y: Int) {
    fun move(move: Pair<Int, Int>) = Position(x + move.first, y + move.second)
}

data class Seats(val rows: List<String>) {

    fun iterate() = Seats(rows.mapIndexed { y, row ->
        row.mapIndexed { x, _ -> iterate(Position(x, y)) }.joinToString("")
    })

    private fun iterate(position: Position) = when {
        at(position) == EMPTY && around(position).none { it == OCCUPIED } -> OCCUPIED
        at(position) == OCCUPIED && around(position).count { it == OCCUPIED } >= 4 -> EMPTY
        else -> at(position)
    }

    fun around(pos: Position) =
        directions.mapNotNull { at(pos.move(it)) }

    fun aroundUntilSeat(pos: Position) =
        directions.mapNotNull { visibleSeatFrom(pos, it) }

    private tailrec fun visibleSeatFrom(currentPos: Position, move: Pair<Int, Int>): Char? =
        when (at(currentPos.move(move))) {
            FLOOR -> visibleSeatFrom(currentPos.move(move), move)
            else -> at(currentPos.move(move))
        }

    fun count(status: Char) = rows.joinToString("").count { it == status }

    fun at(position: Position) =
        rows.getOrNull(position.y)?.getOrNull(position.x)

    override fun toString() = rows.joinToString("\n")

    companion object {
        private val directions = listOf(0 to -1, 1 to -1, 1 to 0, 1 to 1, 0 to 1, -1 to 1, -1 to 0, -1 to -1)
        val OCCUPIED = '#'
        val EMPTY = 'L'
        val FLOOR = '.'
    }
}

tailrec fun Seats.iterateUntilStable(): Seats {
    val iteration = iterate()
    if (this == iteration) return this

    return iteration.iterateUntilStable()
}

check(null == sampleInput.at(Position(-1, 10)))
check('L' == sampleInput.at(Position(1, 1)))
check('.' == sampleInput.at(Position(3, 2)))
check(6 == sampleInput.around(Position(1, 1)).count { it == Seats.EMPTY })
check(2 == sampleInput.around(Position(1, 1)).count { it == Seats.FLOOR })
check(37 == sampleInput.iterateUntilStable().count(Seats.OCCUPIED))

val path = "${Paths.get("").toAbsolutePath()}/input/11.txt"
val input = Scanner(FileInputStream(File(path))).asSequence().toList().let { Seats(it) }

println(input.iterateUntilStable().count(Seats.OCCUPIED)) // 2152

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

