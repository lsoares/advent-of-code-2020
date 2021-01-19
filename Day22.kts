import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths
import java.util.*

// --- Part One ---
val sampleInput = listOf(9, 2, 6, 3, 1) to listOf(5, 8, 4, 7, 10)

class Game(deck1: List<Int>, deck2: List<Int>) {

    private val deck1Cards = deck1.toMutableList()
    private val deck2Cards = deck2.toMutableList()
    private var round = 0
    private val gameOver get() = deck1Cards.isEmpty() || deck2Cards.isEmpty()

    fun autoPlay(): Game {
        while (!gameOver) play()
        return this
    }

    fun calcWinnerScore(): Int {
        if (!gameOver) error("no winner")
        val winnerDeck = if (deck1Cards.isEmpty()) deck2Cards else deck1Cards
        return winnerDeck.withIndex().sumBy {
            (winnerDeck.size - it.index) * it.value
        }
    }

    fun play() {
        round++
        val play1 = deck1Cards.removeFirst()
        val play2 = deck2Cards.removeFirst()
        when {
            play1 > play2 -> deck1Cards.addAll(listOf(play1, play2))
            play1 < play2 -> deck2Cards.addAll(listOf(play2, play1))
            else -> error("tied")
        }
    }
}

val sampleGame = Game(sampleInput.first, sampleInput.second)
check(306 == sampleGame.autoPlay().calcWinnerScore())

val game1 = Scanner(FileInputStream(File("${Paths.get("").toAbsolutePath()}/input/22.txt")))
    .useDelimiter(System.lineSeparator())
    .asSequence()
    .filterNot { it.startsWith("Player") }
    .toList()
    .let {
        Game(
            deck1 = it.subList(0, it.indexOf("")).map(String::toInt),
            deck2 = it.subList(it.indexOf("") + 1, it.size).map(String::toInt),
        )
    }

check(35202 == game1.autoPlay().calcWinnerScore())