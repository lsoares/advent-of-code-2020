val sampleInput = """
    class: 1-3 or 5-7
    row: 6-11 or 33-44
    seat: 13-40 or 45-50

    your ticket:
    7,1,14

    nearby tickets:
    7,3,47
    40,4,50
    55,2,20
    38,6,12
""".split(System.lineSeparator()).asSequence().let(::parse)

data class Notes(val rules: Set<Rule>, val ticket: Ticket, val nearbyTickets: Set<Ticket>)
data class Rule(val title: String, val value1: Pair<Int, Int>, val value2: Pair<Int, Int>)
typealias Ticket = List<Int>

fun parse(input: Sequence<String>) =
    Notes(
        rules = input
            .mapNotNull {
                it.takeUnless { it.startsWith("your ticket:") }
            }
            .filter { it.isBlank() }
            .map {
                it.split(":").let {
                    val values = it.last().split(" or ").map {
                        it.first().toInt() to it.last().toInt()
                    }
                    Rule(
                        title = it.first(),
                        value1 = values.first(),
                        value2 = values.last(),
                    )
                }
            }
            .toSet(),
        ticket = emptyList(),
        nearbyTickets = emptySet(),
    )


println(sampleInput)