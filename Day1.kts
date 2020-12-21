import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths
import java.util.Scanner
import java.net.URL

// part 1
fun solvePair(input: List<Int>) = input
    .cartesianProduct()
    .first { it.first + it.second == 2020 }
    .let { it.first * it.second }

fun <S> List<S>.cartesianProduct(): List<Pair<S, S>> =
    flatMap { j -> List(size) { i -> Pair(j, this[i]) } }

val testInput = listOf(1721, 979, 366, 299, 675, 1456)
check(514579 == solvePair(testInput))

val path = "${Paths.get("").toAbsolutePath()}/input1.txt"
val input = Scanner(FileInputStream(File(path))).asSequence().map { it.toInt() }.toList()
println(solvePair(input))

// part 2
fun solveTriple(input: List<Int>) = input
    .cartesianProduct()
    .flatMap { pair -> List(input.size) { Triple(pair.first, pair.second, input[it]) } }
    .first { it.first + it.second + it.third == 2020 }
    .let { it.first * it.second * it.third }

check(241861950 == solveTriple(testInput))
println(solveTriple(input))
