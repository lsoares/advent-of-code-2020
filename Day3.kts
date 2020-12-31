import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths
import java.util.*

// --- Part One ---
val sampleInput = listOf(
    "..##.........##.........##.........##.........##.........##.......",
    "#...#...#..#...#...#..#...#...#..#...#...#..#...#...#..#...#...#..",
    ".#....#..#..#....#..#..#....#..#..#....#..#..#....#..#..#....#..#.",
    "..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#",
    ".#...##..#..#...##..#..#...##..#..#...##..#..#...##..#..#...##..#.",
    "..#.##.......#.##.......#.##.......#.##.......#.##.......#.##.....",
    ".#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#",
    ".#........#.#........#.#........#.#........#.#........#.#........#",
    "#.##...#...#.##...#...#.##...#...#.##...#...#.##...#...#.##...#...",
    "#...##....##...##....##...##....##...##....##...##....##...##....#",
    ".#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#"
)

check(7 == countTrees(sampleInput))

fun countTrees(input: List<String>) = countTrees(input, 3, 1)

val path = "${Paths.get("").toAbsolutePath()}/input/3.txt"
val input = Scanner(FileInputStream(File(path))).asSequence().toList()

check(247 == countTrees(input))

// --- Part Two ---
check(336L == countTreesMultiplePredicates(sampleInput))

fun countTrees(input: List<String>, moveRight: Int, moveDown: Int) = input
    .filterIndexed { index, _ -> index % moveDown == 0 }
    .filterIndexed { index, value -> value[index * moveRight % value.length] == '#' }
    .size

fun countTreesMultiplePredicates(input: List<String>) =
    setOf(1 to 1, 3 to 1, 5 to 1, 7 to 1, 1 to 2)
        .map { countTrees(input, it.first, it.second).toLong() }
        .reduce(Long::times)

check(2983070376 == countTreesMultiplePredicates(input))
