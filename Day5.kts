import java.io.File
import java.io.FileInputStream
import java.lang.Integer.parseInt
import java.nio.file.Paths
import java.util.*

// part 1
check(820 == findHighestSeatId(sequenceOf("FBFBBFFRLR", "BFFFBBFRRR", "FFFBBBFRRR", "BBFFBBFRLL")))

fun findHighestSeatId(input: Sequence<String>) = input.map(::toBoardingPassId).maxOrNull()

fun toBoardingPassId(boardingSpec: String) =
    convertPart(boardingSpec.dropLast(3)) * 8 + convertPart(boardingSpec.drop(7))

fun convertPart(boardingPass: String) = boardingPass
    .replace(Regex("[FL]"), "0")
    .replace(Regex("[BR]"), "1")
    .let { parseInt(it, 2) }

fun loadFile() = Scanner(FileInputStream(File("${Paths.get("").toAbsolutePath()}/input/5.txt")))
    .asSequence()

println(findHighestSeatId(loadFile()))

// part 2
fun findMissingBoardingId(input: Sequence<String>) = input
    .map(::toBoardingPassId)
    .toList()
    .sorted()
    .windowed(2)
    .map { it.first() to it[1] - it[0] }
    .first { it.second != 1 }
    .first + 1

println(findMissingBoardingId(loadFile()))
