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

    val rulesByName by lazy {
        rules.map { it.title to it }.toMap()
    }

    val validNearbyTickets by lazy {
        nearbyTickets.filter {
            it.getInvalidValues(rules).isEmpty()
        }
    }

    val ticketNumbersByIndex by lazy {
        (0 until ticket.values.size).map { pos ->
            validNearbyTickets.map { ticket -> ticket.values[pos] }
        }
    }

    val errors by lazy {
        nearbyTickets.flatMap {
            it.getInvalidValues(rules)
        }
    }

    val decodedTicket by lazy {
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
            .values
            .mapIndexed { index, rule ->
                rule to ticket.values[index]
            }
    }

    data class Rule(val title: String, val range1: IntRange, val range2: IntRange) {
        operator fun contains(value: Int) = value in range1 || value in range2
        override fun toString() = title
    }

    data class Ticket(val values: List<Int>) {
        fun getInvalidValues(rules: Set<Rule>) =
            values.filter { value ->
                rules.none { value in it }
            }
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

println(sampleInput1.decodedTicket)
println(sampleInput2.decodedTicket)
println(puzzleInput.decodedTicket)

val puzzleAnswer = puzzleInput
    .decodedTicket
    .filter { it.first.title.startsWith("departure") }
    .map { it.second.toBigInteger() }
    .reduce { acc, i -> acc * i }

println(puzzleAnswer)

/*
      IN-    c   r   s
0ª  3/15/5   -   !   -    r       r
1ª  9/1/14   !   x   -   cr       c
2ª  18/5/9   x   x   !   crs      s

1. por cada idx, por cada regra, validar valores de idx contra regra
   0 r
   1 cr
   2 crs
(0r) (1c 1r) (2c 2r 2s)
2. ordenar por tamanho de regras
3. escolher regra por idx:
   a primeira deve estar sozinha fica logo escolhida
   as já escolhidas nao contam
   (fold)


 */

//Based on the nearby tickets in the above example, the first position must be row,
// the second position must be class,
// and the third position must be seat; you can conclude that in your ticket, class is 12, row is 11, and seat is 13.
