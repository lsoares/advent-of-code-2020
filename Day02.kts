import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths
import java.util.*

// --- Part One ---
val sampleInput = listOf(
    Policy(1, 3, 'a') to "abcde",
    Policy(1, 3, 'b') to "cdefg",
    Policy(2, 9, 'c') to "ccccccccc"
)
check(2 == checkValidity(sampleInput.asSequence(), ::validate1))

fun checkValidity(input: Sequence<Pair<Policy, String>>, validator: (Pair<Policy, String>) -> Boolean) =
    input.count { validator(it) }

fun validate1(row: Pair<Policy, String>) =
    row.second.count { row.first.char == it } in row.first.int1..row.first.int2

data class Policy(val int1: Int, val int2: Int, val char: Char)

fun getPuzzleInput() =
    Scanner(FileInputStream(File("${Paths.get("").toAbsolutePath()}/input/2.txt")))
        .useDelimiter(System.lineSeparator()).asSequence()
        .map {
            val row = it.split(" ")
            Policy(
                int1 = row[0].takeWhile { it != '-' }.toInt(),
                int2 = row[0].takeLastWhile { it != '-' }.toInt(),
                char = row[1].first()
            ) to row.last()
        }

check(483 == checkValidity(getPuzzleInput(), ::validate1))

// --- Part Two ---
check(1 == checkValidity(sampleInput.asSequence(), ::validate2))

fun validate2(row: Pair<Policy, String>) =
    (row.second[row.first.int1 - 1] == row.first.char) xor (row.second[row.first.int2 - 1] == row.first.char)

check(482 == checkValidity(getPuzzleInput(), ::validate2))
