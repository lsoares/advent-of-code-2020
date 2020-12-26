import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths
import java.util.*

// --- Part One ---
val sampleInput = listOf<Long>(35, 20, 15, 25, 47, 40, 62, 55, 65, 95, 102, 117, 150, 182, 127, 219, 299, 277, 309, 576)

fun findWrong(preambleSize: Int, input: List<Long>): Long {
    val valid = input.take(preambleSize).nChoose2().map { it.first + it.second }.distinct()
    val current = input.drop(preambleSize).first()
    return if (valid.contains(current))
        findWrong(preambleSize, input.drop(1))
    else current
}

fun <T> List<T>.nChoose2() =
    flatMap { i -> mapNotNull { j -> i.takeIf { it != j }?.let { i to j } } }

check(127L == findWrong(5, sampleInput))

val path = "${Paths.get("").toAbsolutePath()}/input/9.txt"
val input = Scanner(FileInputStream(File(path))).useDelimiter("\n").asSequence()
    .map(String::toLong).toList()

println(findWrong(25, input)) // 15690279


