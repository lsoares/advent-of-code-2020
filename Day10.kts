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

fun getCount1And3(input: List<Int>) = input
    .sorted()
    .let { listOf(0) + it + (it.last() + 3) }
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
check(8L == countPossibilities(sampleInput1))
check(19208L == countPossibilities(sampleInput2))

fun countPossibilities(input: List<Int>) = input
    .sorted()
    .fold(mapOf(0 to 1L)) { counts, jolts ->
        val prev1 = counts.getOrDefault(jolts - 1, 0)
        val prev2 = counts.getOrDefault(jolts - 2, 0)
        val prev3 = counts.getOrDefault(jolts - 3, 0)
        counts + (jolts to (prev1 + prev2 + prev3))
    }
    .values
    .last()

println(countPossibilities(input)) // 13816758796288