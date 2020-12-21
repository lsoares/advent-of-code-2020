import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths
import java.util.*
import java.util.regex.Pattern

fun checkValidity(input: List<Pair<Policy, String>>) =
    input.count { it.first.validate(it.second) }

data class Policy(val min: Int, val max: Int, val letter: Char) {
    fun validate(password: String) = password.count { letter == it } in min..max
}

val basicTest = checkValidity(
    listOf(
        Policy(1, 3, 'a') to "abcde",
        Policy(1, 3, 'b') to "cdefg",
        Policy(2, 9, 'c') to "ccccccccc"
    )
)
check(2 == basicTest)

val path = "${Paths.get("").toAbsolutePath()}/input2.txt"
val input = Scanner(FileInputStream(File(path))).useDelimiter("\n").asSequence()
    .map {
        val row = it.split(" ")
        Policy(
            min = row[0].split("-").first().toInt(),
            max = row[0].split("-").last().toInt(),
            letter = row[1].first()
        ) to row.last()
    }
    .toList()

println(checkValidity(input))