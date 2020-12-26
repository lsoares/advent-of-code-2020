import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths
import java.util.*

val sampleInput = listOf(16, 10, 15, 5, 1, 11, 7, 19, 6, 12, 4)

fun solve(input: List<Int>) = input
    .sorted()
    .let { listOf(0) + it + (it.last() + 3) }
    .windowed(2)
    .map { it.last() - it.first() }
    .groupingBy { it }
    .eachCount()
    .let { it[1]!! * it[3]!! }

check(35 == solve(sampleInput))

val path = "${Paths.get("").toAbsolutePath()}/input/10.txt"
val input = Scanner(FileInputStream(File(path))).asSequence().map(String::toInt).toList()

println(solve(input)) // 2760
