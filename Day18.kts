import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths
import java.util.*

// --- Part One ---
fun String.asEquation() = replace("\\s+".toRegex(), "").iterator()

check(71L == solve1("1 + 2 * 3 + 4 * 5 + 6".asEquation()))
check(51L == solve1("1 + (2 * 3) + (4 * (5 + 6))".asEquation()))
check(26L == solve1("2 * 3 + (4 * 5)".asEquation()))
check(437L == solve1("5 + (8 * 3 + 9 + 3 * 4 * 3)".asEquation()))
check(12240L == solve1("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4)".asEquation()))
check(13632L == solve1("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2".asEquation()))

fun solve1(equation: CharIterator): Long {
    val acc = mutableListOf<Long>()
    var currentOp = '+'

    while (equation.hasNext()) {
        val next = equation.nextChar()
        when (next) {
            '+', '*' -> currentOp = next
            '(' -> acc += solve1(equation)
            ')' -> break
            else -> acc += next.toString().toLong()
        }
        if (acc.size == 2) {
            val a = acc.removeLast()
            val b = acc.removeLast()
            acc += when (currentOp) {
                '*' -> a * b
                '+' -> a + b
                else -> error("unknwon $currentOp")
            }
        }
    }
    return acc.single()
}

fun getPuzzleInput() = Scanner(FileInputStream(File("${Paths.get("").toAbsolutePath()}/input/18.txt")))
    .useDelimiter(System.lineSeparator())
    .asSequence()

val puzzleAnswer1 = getPuzzleInput().sumByDouble {
    solve1(it.asEquation()).toDouble()
}.toLong()

check(11297104473091L == puzzleAnswer1)

// --- Part Two ---
check(231L == solve2("1 + 2 * 3 + 4 * 5 + 6".asEquation()))
check(51L == solve2("1 + (2 * 3) + (4 * (5 + 6))".asEquation()))
check(46L == solve2("2 * 3 + (4 * 5)".asEquation()))
check(1445L == solve2("5 + (8 * 3 + 9 + 3 * 4 * 3)".asEquation()))
check(669060L == solve2("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))".asEquation()))
check(23340L == solve2("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2".asEquation()))

fun solve2(equation: CharIterator): Long {
    var tempSum = 0L
    val toMultiply = mutableListOf<Long>()

    while (equation.hasNext()) {
        val next = equation.nextChar()
        when (next) {
            '*' -> {
                toMultiply += tempSum
                tempSum = 0
            }
            '+' -> continue
            '(' -> tempSum += solve2(equation)
            ')' -> break
            else -> tempSum += next.toString().toLong()
        }
    }
    toMultiply += tempSum
    return toMultiply.reduce { acc, i -> acc * i }
}

val puzzleAnswer2 = getPuzzleInput().sumByDouble {
    solve2(it.asEquation()).toDouble()
}.toLong()

check(185348874183674L == puzzleAnswer2)
