import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths
import java.util.Scanner
import java.net.URL

// --- Part One ---
val sampleInput = listOf(1721, 979, 366, 299, 675, 1456)
check(514579 == solvePair(sampleInput))

fun solvePair(input: List<Int>) = input
    .cartesianProduct()
    .first { it.first + it.second == 2020 }
    .let { it.first * it.second }

fun <S> List<S>.cartesianProduct(): List<Pair<S, S>> =
    flatMap { j -> List(size) { i -> Pair(j, get(i)) } }

val path = "${Paths.get("").toAbsolutePath()}/input/1.txt"
val puzzleInput = Scanner(FileInputStream(File(path))).asSequence().map(String::toInt).toList()

check(440979 == solvePair(puzzleInput))

// --- Part Two ---
check(241861950 == solveTriple(sampleInput))

fun solveTriple(input: List<Int>) = input
    .cartesianProduct()
    .flatMap { pair -> List(input.size) { Triple(pair.first, pair.second, input[it]) } }
    .first { it.first + it.second + it.third == 2020 }
    .let { it.first * it.second * it.third }

check(82498112 == solveTriple(puzzleInput))
