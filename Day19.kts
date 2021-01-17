val sampleInput = """
       0: 4 1 5
       1: 2 3 | 3 2
       2: 4 4 | 5 5
       3: 4 5 | 5 4
       4: "a"
       5: "b"
       
       ababbb
       bababa
       abbbab
       aaabbb
       aaaabbb
       """.trimIndent()
    .split("\n")
    .asSequence()
    .let(::parse)

interface Rule
data class RuleRef(val targets: Set<List<Int>>) : Rule
data class RuleAlpha(val char: Char) : Rule
data class Data(val rules: Map<Int, Rule>, val messages: Set<String>)

fun parse(input: Sequence<String>) = input
    .filter(String::isNotBlank)
    .partition { it.first().isDigit() }
    .let { (rules, messages) ->
        val rulesMap = rules
            .map {
                it.split(": ").let {
                    val rule: Rule = if (it.last().contains("\""))
                        RuleAlpha(it.last()[1])
                    else
                        RuleRef(it.last().split(" | ").map {
                            it.split(" ").map { it.toInt() }
                        }.toSet())

                    it.first().toInt() to rule
                }
            }
            .toMap()
        Data(rulesMap, messages.toSet())
    }

println(sampleInput)

