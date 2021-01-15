import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths
import java.util.*

// --- Part One ---
check(71L == solve("1 + 2 * 3 + 4 * 5 + 6".iterator()))
check(51L == solve("1 + (2 * 3) + (4 * (5 + 6))".iterator()))
check(26L == solve("2 * 3 + (4 * 5)".iterator()))
check(437L == solve("5 + (8 * 3 + 9 + 3 * 4 * 3)".iterator()))
check(12240L == solve("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4)".iterator()))
check(13632L == solve("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2".iterator()))

fun solve(equation: CharIterator): Long {
    val acc = mutableListOf<Long>()
    var op = '+'
    while (equation.hasNext()) {
        val next = equation.nextChar()
        when (next) {
            ' ' -> continue
            '+', '*' -> op = next
            '(' -> acc += solve(equation)
            ')' -> break
            else -> acc += next.toString().toLong()
        }
        if (acc.size == 2) {
            val a = acc.removeLast()
            val b = acc.removeLast()
            acc += when (op) {
                '*' -> a * b
                '+' -> a + b
                else -> error("unknwon $op")
            }
        }
    }
    return acc.first()
}

val puzzleInput = Scanner(FileInputStream(File("${Paths.get("").toAbsolutePath()}/input/18.txt")))
    .useDelimiter(System.lineSeparator())
    .asSequence()

val puzzleAnswer1 = puzzleInput.sumByDouble {
    solve(it.iterator()).toDouble()
}.toLong()

check(11297104473091L == puzzleAnswer1)