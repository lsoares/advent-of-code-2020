import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths
import java.util.*

// --- Part One ---
val sampleInput1 = listOf(16, 10, 15, 5, 1, 11, 7, 19, 6, 12, 4)
val sampleInput2 = listOf(
    28, 33, 18, 42, 31, 14, 46, 20, 48, 47, 24, 23, 49, 45, 19,
    38, 39, 11, 1, 32, 25, 35, 8, 17, 7, 9, 4, 2, 34, 10, 3
)

fun List<Int>.prepareData() = sorted()
    .let { listOf(0) + it + (it.last() + 3) }

fun getCount1And3(input: List<Int>) = input
    .prepareData()
    .windowed(2)
    .map { it.last() - it.first() }
    .groupingBy { it }
    .eachCount()
    .let { it[1]!! * it[3]!! }

check(35 == getCount1And3(sampleInput1))
check(220 == getCount1And3(sampleInput2))

val path = "${Paths.get("").toAbsolutePath()}/input/10.txt"
val input = Scanner(FileInputStream(File(path))).asSequence().map(String::toInt).toList()

println(getCount1And3(input)) // 2760

// --- Part Two ---
fun countPossibilities(input: List<Int>) = input.prepareData().let {
    it.foldIndexed(mapOf<Int, Long>()) { i, acc, _ ->
        var d1 = it.diff(i, i - 1).toLong()
        var d2 = it.diff(i, i - 2).toLong()
        var d3 = it.diff(i, i - 3).toLong()
        if (i == 0) {
            d1 = 1L
            d2 = 4L
            d3 = 4L
        }
        if (i == 1) {
            d2 = 4L
            d3 = 4L
        }
        if (i == 2) {
            d3 = 4L
        }

        var sum = (if (d1 <= 3L) acc.getOrDefault(i - 1, 0) else 0L) +
                (if (d2 <= 3L) acc.getOrDefault(i - 2, 0) else 0L) +
                (if (d3 <= 3L) acc.getOrDefault(i - 3, 0) else 0L)
        if (i == 0) sum = 1
        acc.plus(i to sum)
    }.values.last()
}

fun List<Int>.diff(i1: Int, i0: Int) =
    getOrElse(i1) { return 0 } - getOrElse(i0) { return 0 }

check(8L == countPossibilities(sampleInput1))
check(19208L == countPossibilities(sampleInput2))

println(countPossibilities(input)) // 13816758796288