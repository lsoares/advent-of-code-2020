import java.io.File
import java.io.FileInputStream
import java.lang.Integer.parseInt
import java.nio.file.Paths
import java.util.*

// part 1
val sampleInput = sequenceOf("FBFBBFFRLR", "BFFFBBFRRR", "FFFBBBFRRR", "BBFFBBFRLL")

check(820 == findHighestSeatId(sampleInput))

fun findHighestSeatId(input: Sequence<String>) = input.map(::convert).maxOrNull()

fun convert(boardingSpec: String) = convertPart(boardingSpec.dropLast(3)) * 8 + convertPart(boardingSpec.drop(7))

fun convertPart(boardingPass: String) = boardingPass
    .replace(Regex("[FL]"), "0")
    .replace(Regex("[BR]"), "1")
    .let { parseInt(it, 2) }

fun loadFile() = Scanner(FileInputStream(File("${Paths.get("").toAbsolutePath()}/input/5.txt")))
    .asSequence()

println(findHighestSeatId(loadFile()))
