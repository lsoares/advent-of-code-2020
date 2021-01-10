import Day16.Notes.Rule
import Day16.Notes.Ticket
import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths
import java.util.*

// --- Part One ---
val sampleInput1 = listOf(
    "class: 1-3 or 5-7",
    "row: 6-11 or 33-44",
    "seat: 13-40 or 45-50",

    "your ticket:", "7,1,14",

    "nearby tickets:",
    "7,3,47",
    "40,4,50",
    "55,2,20",
    "38,6,12"
).let(::parse)

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
            .map(String::toInt)
            .let(::Ticket),
        nearbyTickets = input
            .dropWhile { !it.startsWith("nearby tickets:") }
            .drop(1)
            .map { it.split(",").map(String::toInt).let(::Ticket) }
            .toSet(),
    )

check(71 == sampleInput1.errors.sum())

data class Notes(val rules: Set<Rule>, val ticket: Ticket, val nearbyTickets: Set<Ticket>) {

    val validNearbyTickets by lazy {
        nearbyTickets.filter {
            it.getInvalidValues(rules).isEmpty()
        }
    }

    val ticketNumbersByIndex by lazy {
        (0 until ticket.size).map { pos ->
            validNearbyTickets.map { ticket -> ticket[pos] }
        }
    }

    val errors by lazy {
        nearbyTickets.flatMap {
            it.getInvalidValues(rules)
        }
    }

    val rulesOrder by lazy {
        ticketNumbersByIndex
            .map { valuesAtIndex ->
                rules.map { rule ->
                    rule to valuesAtIndex.all { it in rule }
                }.toMap()
            }
            .withIndex()
            .sortedBy {
                it.value.count { it.value }
            }
            .fold(mapOf<Int, Rule>()) { acc, indexedValue: IndexedValue<Map<Rule, Boolean>> ->
                val value = indexedValue.value.entries.first {
                    it.value && it.key !in acc.values
                }
                acc + (indexedValue.index to value.key)
            }
            .entries
            .sortedBy { it.key }
            .map { it.value }
            .toList()
    }

    val decodedTicket by lazy {
        rulesOrder.mapIndexed { index: Int, rule: Rule ->
            rule to ticket[index]
        }
    }

    data class Rule(val title: String, private val range1: IntRange, private val range2: IntRange) {
        operator fun contains(value: Int) = value in range1 || value in range2
        override fun toString() = title
    }

    data class Ticket(private val values: List<Int>) {
        fun getInvalidValues(rules: Set<Rule>) =
            values.filter { value ->
                rules.none { value in it }
            }

        val size get() = values.size
        operator fun get(index: Int) = values[index]
    }
}

val path = "${Paths.get("").toAbsolutePath()}/input/16.txt"
val puzzleInput = Scanner(FileInputStream(File(path)))
    .useDelimiter(System.lineSeparator()).asSequence()
    .filter(String::isNotBlank)
    .toList().let(::parse)

check(26941 == puzzleInput.errors.sum())

// --- Part Two ---
val sampleInput2 = listOf(
    "class: 0-1 or 4-19",
    "row: 0-5 or 8-19",
    "seat: 0-13 or 16-19",

    "your ticket: ", "11,12,13",

    "nearby tickets: ",
    "3,9,18",
    "15,1,5",
    "5,14,9",
).let(::parse)

check(listOf("row", "class", "seat") == sampleInput1.rulesOrder.map { it.title })
check(listOf("row", "class", "seat") == sampleInput2.rulesOrder.map { it.title })

val puzzleAnswer = puzzleInput
    .decodedTicket
    .filter { it.first.title.startsWith("departure") }
    .map { it.second.toLong() }
    .reduce { acc, i -> acc * i }

check(634796407951L == puzzleAnswer)
