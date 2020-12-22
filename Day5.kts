import java.io.File
import java.io.FileInputStream
import java.lang.Math.pow
import java.nio.file.Paths
import java.util.*

// part 1
val sampleInput = sequenceOf("FBFBBFFRLR", "BFFFBBFRRR", "FFFBBBFRRR", "BBFFBBFRLL")

check(820 == findHighestSeatId(sampleInput))

fun findHighestSeatId(input: Sequence<String>) = input.map(::convert).maxOrNull()

fun convert(input: String) = convertPart(input.dropLast(3)) * 8 + convertPart(input.drop(7))

fun convertPart(input: String) = input
    .foldIndexed(0 to pow(2.0, input.length - 1.0)) { index, acc, c ->
        val weight = pow(2.0, input.length - 1.0 - index).toInt()
        when (c) {
            'F', 'L' -> acc.first to (acc.second - weight)
            'B', 'R' -> (acc.first + weight) to acc.second
            else -> error("can't process")
        }
    }.first

fun loadFile() = Scanner(FileInputStream(File("${Paths.get("").toAbsolutePath()}/input/5.txt")))
    .asSequence()

println(findHighestSeatId(loadFile()))