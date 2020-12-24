import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths
import java.util.*

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

fun buildInverted(sentences: List<String>): Map<String, List<String>> {
    val entries = mutableMapOf<String, MutableList<String>>()
    sentences.forEach {
        it.removeSuffix(".")
            .replace(" bags?".toRegex(), "")
            .replace("no other", "0")
            .split(" contain ", ", ")
            .let { parts ->
                val parent = parts.first().replace("\\d ".toRegex(), "")
                parts.drop(1).forEach() {
                    val entry = it.replace("\\d ".toRegex(), "")
                    entries.putIfAbsent(entry, mutableListOf())
                    entries.get(entry)?.add(parent)
                }
            }
    }
    return entries
}

fun countPossibleBagColors(input: List<String>, bagColor: String): Int {
    val visited = mutableSetOf<String>()

    fun listPossibleBagColors(bagColor: String, tree: Map<String, List<String>>): List<String> =
        setOfNotNull(tree[bagColor]).plus(
            tree.getOrDefault(bagColor, emptyList()).mapNotNull {
                if (visited.contains(it)) return@mapNotNull null
                listPossibleBagColors(it, tree)
            }
        ).flatten()

    return listPossibleBagColors(bagColor, buildInverted(input)).toSet().size
}

check(4 == countPossibleBagColors(sampleInput, "shiny gold"))

val path = "${Paths.get("").toAbsolutePath()}/input/7.txt"
val input = Scanner(FileInputStream(File(path))).useDelimiter("\n").asSequence().toList()

println(countPossibleBagColors(input, "shiny gold"))