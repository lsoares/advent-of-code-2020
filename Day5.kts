import java.io.File
import java.io.FileInputStream
import java.lang.Integer.parseInt
import java.nio.file.Paths
import java.util.*

// --- Part One ---
val sampleIput = sequenceOf("FBFBBFFRLR", "BFFFBBFRRR", "FFFBBBFRRR", "BBFFBBFRLL")

check(820 == findHighestSeatId(sampleIput))

fun findHighestSeatId(input: Sequence<String>) = input.map(::toBoardingPassId).maxOrNull()

fun toBoardingPassId(boardingSpec: String) =
    convertPart(boardingSpec.take(7)) * 8 + convertPart(boardingSpec.takeLast(3))

fun convertPart(boardingPass: String) = boardingPass
    .replace(Regex("[FL]"), "0")
    .replace(Regex("[BR]"), "1")
    .let { parseInt(it, 2) }

fun loadFile() = Scanner(FileInputStream(File("${Paths.get("").toAbsolutePath()}/input/5.txt")))
    .asSequence()

check(806 == findHighestSeatId(loadFile()))

// --- Part Two ---
fun findMissingBoardingId(input: Sequence<String>) = input
    .map(::toBoardingPassId)
    .toList()
    .sorted()
    .windowed(2)
    .map { it.first() to it[1] - it[0] }
    .first { it.second != 1 }
    .first + 1

check(562 == findMissingBoardingId(loadFile()))
