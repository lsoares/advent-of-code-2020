import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

class Test1 {

    @Test
    fun example() {
        val input = listOf(
            1721,
            979,
            366,
            299,
            675,
            1456,
        )

        assertEquals(514579, Challenge1.solve(input))
    }

    @Test
    fun `exercise part 1`() {
        val inputStream = this::class.java.getResourceAsStream("input1.txt")
        val input = Scanner(inputStream).asSequence().map { it.toInt() }.toList()

        assertEquals(440979, Challenge1.solve(input))
    }

    @Test
    fun `part2 example`() {
        val input = listOf(
            1721,
            979,
            366,
            299,
            675,
            1456,
        )

        assertEquals(241861950, Challenge1.solveTriple(input))
    }

    @Test
    fun `exercise part 2`() {
        val inputStream = this::class.java.getResourceAsStream("input1.txt")
        val input = Scanner(inputStream).asSequence().map { it.toInt() }.toList()

        assertEquals(82498112, Challenge1.solveTriple(input))
    }
}