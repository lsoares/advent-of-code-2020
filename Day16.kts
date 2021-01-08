import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths
import java.util.*

// --- Part One ---
val sampleInput = listOf(
    "class: 1-3 or 5-7",
    "row: 6-11 or 33-44",
    "seat: 13-40 or 45-50",
    "your ticket:",
    "7,1,14",
    "nearby tickets:",
    "7,3,47",
    "40,4,50",
    "55,2,20",
    "38,6,12"
).let(::parse)

check(71 == findErrors(sampleInput).sum())

data class Notes(val rules: Set<Rule>, val ticket: Ticket, val nearbyTickets: Set<Ticket>)
data class Rule(val title: String, val range1: IntRange, val range2: IntRange) {
    fun isValid(value: Int) = value in range1 || value in range2
}

typealias Ticket = List<Int>

fun parse(input: List<String>) =
    Notes(
        rules = input
            .takeWhile { !it.startsWith("your ticket:") }
            .map {
                it.split(": ").let {
                    val values = it.last().split(" or ").map {
                        it.split("-").let {
                            it.first().toInt()..it.last().toInt()
                        }
                    }
                    Rule(
                        title = it.first(),
                        range1 = values.first(),
                        range2 = values.last(),
                    )
                }
            }
            .toSet(),
        ticket = input
            .dropWhile { !it.startsWith("your ticket:") }[1]
            .split(",")
            .map(String::toInt),
        nearbyTickets = input
            .dropWhile { !it.startsWith("nearby tickets:") }
            .drop(1)
            .map { it.split(",").map(String::toInt) }
            .toSet(),
    )

fun findErrors(notes: Notes) = notes
    .nearbyTickets.flatMap { ticket ->
        ticket.filter { ticketValue ->
            notes.rules.none { rule ->
                rule.isValid(ticketValue)
            }
        }
    }

val path = "${Paths.get("").toAbsolutePath()}/input/16.txt"
val puzzleInput = Scanner(FileInputStream(File(path)))
    .useDelimiter(System.lineSeparator()).asSequence()
    .filter(String::isNotBlank)
    .toList().let(::parse)

check(26941 == findErrors(puzzleInput).sum())
