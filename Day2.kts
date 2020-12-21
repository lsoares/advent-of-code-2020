import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths
import java.util.*

// part 1
val testInput = listOf(
    Policy(1, 3, 'a') to "abcde",
    Policy(1, 3, 'b') to "cdefg",
    Policy(2, 9, 'c') to "ccccccccc"
)
check(2 == checkValidity(testInput, ::validate1))

fun checkValidity(input: List<Pair<Policy, String>>, validator: (Pair<Policy, String>) -> Boolean) =
    input.count { validator(it) }

fun validate1(row: Pair<Policy, String>) =
    row.second.count { row.first.char == it } in row.first.int1..row.first.int2


data class Policy(val int1: Int, val int2: Int, val char: Char)

val path = "${Paths.get("").toAbsolutePath()}/input2.txt"
val input = Scanner(FileInputStream(File(path))).useDelimiter("\n").asSequence()
    .map {
        val row = it.split(" ")
        Policy(
            int1 = row[0].split("-").first().toInt(),
            int2 = row[0].split("-").last().toInt(),
            char = row[1].first()
        ) to row.last()
    }
    .toList()

println(checkValidity(input, ::validate1))

// part 2
check(1 == checkValidity(testInput, ::validate2))

fun validate2(row: Pair<Policy, String>) =
    (row.second[row.first.int1 - 1] == row.first.char) xor (row.second[row.first.int2 - 1] == row.first.char)

println(checkValidity(input, ::validate2))
