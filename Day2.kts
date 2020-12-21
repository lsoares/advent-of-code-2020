import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths
import java.util.*

fun checkValidity(input: List<Pair<Policy, String>>, validator: (Pair<Policy, String>) -> Boolean) =
    input.count { validator(it) }

fun validate1(row: Pair<Policy, String>) = row.second.count { row.first.arg3 == it } in row.first.arg1..row.first.arg2

val testInput = listOf(
    Policy(1, 3, 'a') to "abcde",
    Policy(1, 3, 'b') to "cdefg",
    Policy(2, 9, 'c') to "ccccccccc"
)
check(2 == checkValidity(testInput, ::validate1))

data class Policy(val arg1: Int, val arg2: Int, val arg3: Char)

val path = "${Paths.get("").toAbsolutePath()}/input2.txt"
val input = Scanner(FileInputStream(File(path))).useDelimiter("\n").asSequence()
    .map {
        val row = it.split(" ")
        Policy(
            arg1 = row[0].split("-").first().toInt(),
            arg2 = row[0].split("-").last().toInt(),
            arg3 = row[1].first()
        ) to row.last()
    }
    .toList()

println(checkValidity(input, ::validate1))

//check(1 == checkValidity2(testInput))
