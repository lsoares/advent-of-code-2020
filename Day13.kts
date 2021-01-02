import java.io.File
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
