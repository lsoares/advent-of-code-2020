import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths
import java.util.*

// part 1
val basicInput = """
..##.........##.........##.........##.........##.........##.......
#...#...#..#...#...#..#...#...#..#...#...#..#...#...#..#...#...#..
.#....#..#..#....#..#..#....#..#..#....#..#..#....#..#..#....#..#.
..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#
.#...##..#..#...##..#..#...##..#..#...##..#..#...##..#..#...##..#.
..#.##.......#.##.......#.##.......#.##.......#.##.......#.##.....
.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#
.#........#.#........#.#........#.#........#.#........#.#........#
#.##...#...#.##...#...#.##...#...#.##...#...#.##...#...#.##...#...
#...##....##...##....##...##....##...##....##...##....##...##....#
.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#""".trim().split("\n")

check(7 == countTrees(basicInput))

fun countTrees(input: List<String>) = countTrees(input, 3, 1)

val path = "${Paths.get("").toAbsolutePath()}/input/3.txt"
val input = Scanner(FileInputStream(File(path))).asSequence().toList()
println(countTrees(input))

// part 2
check(336L == countTreesMultiplePredicates(basicInput))

fun countTrees(input: List<String>, moveRight: Int, moveDown: Int) = input
    .filterIndexed { index, _ -> index % moveDown == 0 }
    .filterIndexed { index, value -> value[index * moveRight % value.length] == '#' }
    .size

fun countTreesMultiplePredicates(input: List<String>) =
    setOf(1 to 1, 3 to 1, 5 to 1, 7 to 1, 1 to 2)
        .map { countTrees(input, it.first, it.second).toLong() }
        .reduce(Long::times)

println(countTreesMultiplePredicates(input))
