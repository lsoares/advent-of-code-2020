import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths
import java.util.*

// --- Part One ---
val sampleInput = listOf(
    "light red bags contain 1 bright white bag, 2 muted yellow bags.",
    "dark orange bags contain 3 bright white bags, 4 muted yellow bags.",
    "bright white bags contain 1 shiny gold bag.",
    "muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.",
    "shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.",
    "dark olive bags contain 3 faded blue bags, 4 dotted black bags.",
    "vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.",
    "faded blue bags contain no other bags.",
    "dotted black bags contain no other bags.",
)

check(4 == countPossibleBagColors(sampleInput, "shiny gold"))

fun buildRules(sentences: List<String>): Map<String, Map<String, Int>> =
    sentences.map { sentence ->
        sentence.removeSuffix(".")
            .replace(" bags?".toRegex(), "")
            .replace("no other", "0")
            .split(" contain ", ", ")
            .let { partsStr ->
                partsStr.first() to partsStr.drop(1).mapNotNull {
                    it.takeWhile(Char::isDigit).toInt().takeUnless { it == 0 }?.let { count ->
                        it.dropWhile(Char::isDigit).trim() to count
                    }
                }.toMap()
            }
    }.toMap()

fun canHoldColor(rules: Map<String, Map<String, Int>>, currentColor: String, desiredColor: String): Boolean =
    (rules[currentColor] ?: error("not found")).entries.any {
        it.key == desiredColor || canHoldColor(rules, it.key, desiredColor)
    }

fun countPossibleBagColors(input: List<String>, color: String): Int =
    buildRules(input).let { rules ->
        rules.keys.count {
            canHoldColor(rules, it, color)
        }
    }

val path = "${Paths.get("").toAbsolutePath()}/input/7.txt"
val puzzleInput = Scanner(FileInputStream(File(path)))
    .useDelimiter(System.lineSeparator()).asSequence().toList()

check(101 == countPossibleBagColors(puzzleInput, "shiny gold"))

// --- Part Two ---
check(32 == countBags(sampleInput, "shiny gold"))

fun countBags(input: List<String>, desiredColor: String) =
    countBagsRecur(buildRules(input), desiredColor) - 1

fun countBagsRecur(rules: Map<String, Map<String, Int>>, currentBag: String): Int =
    (rules[currentBag] ?: error("not found")).entries.sumBy {
        it.value * countBagsRecur(rules, it.key)
    } + 1

check(108636 == countBags(puzzleInput, "shiny gold"))
