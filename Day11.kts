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
    companion object {
        val OCCUPIED = '#'
        val EMPTY = 'L'
    }

    private fun at(x: Int, y: Int) = rows.getOrNull(y)?.getOrNull(x) ?: 'x'

    private fun around(x: Int, y: Int) = listOfNotNull(
        at(x - 1, y - 1), at(x, y - 1), at(x + 1, y - 1),
        at(x - 1, y), at(x + 1, y),
        at(x - 1, y + 1), at(x, y + 1), at(x + 1, y + 1),
    )

    private fun iterate(x: Int, y: Int) = when {
        at(x, y) == EMPTY && around(x, y).none { it == OCCUPIED } -> OCCUPIED
        at(x, y) == OCCUPIED && around(x, y).count { it == OCCUPIED } >= 4 -> EMPTY
        else -> at(x, y)
    }

    fun iterate() = Seats(rows.mapIndexed { y, row ->
        row.mapIndexed { x, _ -> iterate(x, y) }.joinToString("")
    })

    fun count(status: Char) = rows.sumBy { it.count { it == status } }

    override fun toString() = rows.joinToString("\n")
}

tailrec fun iterateUntilStable(seats: Seats): Seats {
    val iteration = seats.iterate()
    if (seats == iteration) return seats

    return iterateUntilStable(iteration)
}

check(37 == iterateUntilStable(sampleInput).count(Seats.OCCUPIED))

val path = "${Paths.get("").toAbsolutePath()}/input/11.txt"
val input = Scanner(FileInputStream(File(path))).asSequence().toList().let { Seats(it) }

println(iterateUntilStable(input).count(Seats.OCCUPIED)) // 2152