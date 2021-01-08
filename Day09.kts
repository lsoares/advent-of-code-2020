import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths
import java.util.*

// --- Part One ---
val sampleInput = listOf<Long>(35, 20, 15, 25, 47, 40, 62, 55, 65, 95, 102, 117, 150, 182, 127, 219, 299, 277, 309, 576)

check(127L == sampleInput[findWrongIndex(5, sampleInput)])

fun findWrongIndex(preambleSize: Int, input: List<Long>, index: Int = preambleSize): Int {
    val valid = input.take(preambleSize).nChoose2().map { it.first + it.second }.distinct()
    val current = input.drop(preambleSize).firstOrNull() ?: error("not found")
    return if (current in valid)
        findWrongIndex(preambleSize, input.drop(1), index + 1)
    else index
}

fun <T> List<T>.nChoose2() =
    flatMap { i -> mapNotNull { j -> i.takeIf { it != j }?.let { i to j } } }

val path = "${Paths.get("").toAbsolutePath()}/input/9.txt"
val puzzleInput = Scanner(FileInputStream(File(path))).asSequence().map(String::toLong).toList()

check(15690279L == puzzleInput[findWrongIndex(25, puzzleInput)])

// --- Part Two ---
check(62L == findContiguousWrong(5, sampleInput))

fun findContiguousWrong(preambleSize: Int, input: List<Long>): Long {
    val invalidValue = input[findWrongIndex(preambleSize, input)]

    for (i in input.indices) {
        var sum = 0L
        var j = i
        do {
            sum += input[j]
            if (sum == invalidValue && j != i) {
                return input.subList(i, j).let {
                    it.minOrNull()!! + it.maxOrNull()!!
                }
            }
            j++
        } while (sum < invalidValue)
    }
    error("not found")
}

check(2174232L == findContiguousWrong(25, puzzleInput))