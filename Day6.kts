import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths
import java.util.*

// part 1
val sampleInput = sequenceOf(
    listOf("abc"),
    listOf("a", "b", "c"),
    listOf("ab", "ac"),
    listOf("a", "a", "a", "a"),
    listOf("b")
)

check(11 == countAnswers(sampleInput))

fun countAnswers(answers: Sequence<List<String>>) =
    answers.sumBy { it.joinToString("").toList().toSet().size }

fun loadFile() = Scanner(FileInputStream(File("${Paths.get("").toAbsolutePath()}/input/6.txt")))
    .useDelimiter("\n\n")
    .asSequence()
    .map { it.split("\n") }

println(countAnswers(loadFile()))