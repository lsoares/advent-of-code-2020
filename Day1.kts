import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths
import java.util.Scanner
import java.net.URL

// --- Part One ---
val testInput = listOf(1721, 979, 366, 299, 675, 1456)
check(514579 == solvePair(testInput))

fun solvePair(input: List<Int>) = input
    .cartesianProduct()
    .first { it.first + it.second == 2020 }
    .let { it.first * it.second }

fun <S> List<S>.cartesianProduct(): List<Pair<S, S>> =
    flatMap { j -> List(size) { i -> Pair(j, get(i)) } }

val path = "${Paths.get("").toAbsolutePath()}/input/1.txt"
val input = Scanner(FileInputStream(File(path))).asSequence().map { it.toInt() }.toList()

println(solvePair(input)) // 440979

// --- Part Two ---
check(241861950 == solveTriple(testInput))

fun solveTriple(input: List<Int>) = input
    .cartesianProduct()
    .flatMap { pair -> List(input.size) { Triple(pair.first, pair.second, input[it]) } }
    .first { it.first + it.second + it.third == 2020 }
    .let { it.first * it.second * it.third }

println(solveTriple(input)) // 82498112
