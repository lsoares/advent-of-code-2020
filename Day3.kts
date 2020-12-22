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
.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#
""".trim().split("\n")

check(7L == countTrees(basicInput))

fun countTrees(input: List<String>) = countTrees(input, 3, 1)

val path = "${Paths.get("").toAbsolutePath()}/input/3.txt"
val input = Scanner(FileInputStream(File(path))).asSequence().toList()
println(countTrees(input))

// part 2
check(336L == countTreesMultiplePredicates(basicInput))

fun countTrees(input: List<String>, moveRight: Int, moveDown: Int) = input
    .filterIndexed { index, _ -> index % moveDown == 0 }
    .withIndex()
    .count { it.value[it.index * moveRight % it.value.length] == '#' }
    .toLong()

fun countTreesMultiplePredicates(input: List<String>) =
    countTrees(input, 1, 1) *
            countTrees(input, 3, 1) *
            countTrees(input, 5, 1) *
            countTrees(input, 7, 1) *
            countTrees(input, 1, 2)

println(countTreesMultiplePredicates(input))
