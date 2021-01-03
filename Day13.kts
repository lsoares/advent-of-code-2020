import java.io.File
import java.math.BigInteger
import java.nio.file.Paths

val sampleInput = listOf("939", "7,13,x,x,59,x,31,19").let(::parse)

// --- Part One ---
check(295 == findEarliestBus(sampleInput.first, sampleInput.second))

fun parse(input: List<String>) =
    input.first().toInt() to input.last().split(",").filterNot { it == "x" }.map(String::toInt).toSet()

fun findEarliestBus(arrivedAt: Int, buses: Set<Int>) =
    buses.map { busId ->
        busId to arrivedAt - arrivedAt % busId + busId
    }.minByOrNull { (_, nextBus) ->
        nextBus
    }?.let { (busId, nextBus) ->
        busId * (nextBus - arrivedAt)
    } ?: error("bus not found")

val input = File("${Paths.get("").toAbsolutePath()}/input/13.txt")
    .readLines().let(::parse)

check(5257 == findEarliestBus(input.first, input.second))

// --- Part Two ---
check(3417.toBigInteger() == solve2(listOf(17, 0, 13, 19)))
check(754018.toBigInteger() == solve2(listOf(67, 7, 59, 61)))
check(779210.toBigInteger() == solve2(listOf(67, 0, 7, 59, 61)))
check(1261476.toBigInteger() == solve2(listOf(67, 7, 0, 59, 61)))
check(1202161486.toBigInteger() == solve2(listOf(1789, 37, 47, 1889)))

fun solve2(buses: List<Int>): BigInteger {
    val zero = 0.toBigInteger()
    val busesI = buses
        .map { it.toBigInteger() }
        .withIndex()
        .filter { it.value != zero }
    var step = busesI.first().value
    var time = zero
    busesI.drop(1).forEach { (i, bus) ->
        while ((time.plus(i.toBigInteger())).mod(bus) != zero) {
            time = time.plus(step)
        }
        step = step.times(bus)
    }
    return time
}

val input2 = File("${Paths.get("").toAbsolutePath()}/input/13.txt")
    .readLines().last().split(",").map { if (it == "x") 0 else it.toInt() }

check(538703333547789.toBigInteger() == solve2(input2))
