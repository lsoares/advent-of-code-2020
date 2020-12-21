import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths
import java.util.Scanner

fun solvePair(input: List<Int>) = input
    .cartesianProduct(input)
    .first { it.first + it.second == 2020 }
    .let { it.first * it.second }

fun <S> List<S>.cartesianProduct(other: List<S>): List<Pair<S, S>> =
    flatMap { List(other.size) { i -> Pair(it, other[i]) } }

fun solveTriple(input: List<Int>) = input
    .cartesianProduct(input)
    .flatMap { pair -> List(input.size) { Triple(pair.first, pair.second, input[it]) } }
    .first { it.first + it.second + it.third == 2020 }
    .let { it.first * it.second * it.third }


val testInput = listOf(1721, 979, 366, 299, 675, 1456)
check(514579 == solvePair(testInput))
check(241861950 == solveTriple(testInput))

val path = Paths.get("").toAbsolutePath().toString() + "/input1.txt"
val input = Scanner(FileInputStream(File(path))).asSequence().map { it.toInt() }.toList()

println(solvePair(input))
println(solveTriple(input))
