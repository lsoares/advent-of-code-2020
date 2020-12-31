import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths
import java.util.*

// --- Part One ---
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

check(6633 == countAnswers(loadFile()))

// --- Part Two ---
check(6 == countAnswers2(sampleInput))

fun countAnswers2(answers: Sequence<List<String>>) =
    answers.sumBy { groupAnswers ->
        countAnswer(groupAnswers).count() { it.value == groupAnswers.size }
    }

fun countAnswer(groupAnswers: List<String>): Map<Char, Int> =
    groupAnswers.fold(emptyMap()) { acc, ans ->
        acc + ans.map {
            it to (acc.getOrDefault(it, 0) + 1)
        }
    }

check(3202 == countAnswers2(loadFile()))
