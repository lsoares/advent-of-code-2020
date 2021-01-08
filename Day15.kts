// --- Part One ---
check(0 == Game(listOf(0, 3, 6)).at(10))

data class Game(private val startingNumbers: List<Int>) {
    private var usedNumbers = mutableMapOf<Int, Int>()
    private var lastTurn = 0
    private var lastSpokenNumber = -1

    fun at(turn: Int): Int {
        usedNumbers = HashMap<Int, Int>(turn)
        lastTurn = 0
        lastSpokenNumber = -1
        startingNumbers.forEach(::store)
        return usedNumbers.getOrElse(turn - 1, {
            repeat(turn - startingNumbers.size) { calc() }
            lastSpokenNumber
        })
    }

    private fun calc(): Int {
        val lastNumberUsedAtTurn = usedNumbers.getOrElse(lastSpokenNumber, { return store(0) })
        return when (lastNumberUsedAtTurn) {
            lastTurn -> store(0)
            else -> store(lastTurn - lastNumberUsedAtTurn)
        }
    }

    private fun store(number: Int): Int {
        usedNumbers[lastSpokenNumber] = lastTurn
        lastTurn++
        lastSpokenNumber = number
        return number
    }
}

val puzzleInput = listOf(6, 13, 1, 15, 2, 0)
check(1194 == Game(puzzleInput).at(2020))

// --- Part Two ---
check(175594 == Game(listOf(0, 3, 6)).at(30_000_000))
check(2578 == Game(listOf(1, 3, 2)).at(30_000_000))
check(3544142 == Game(listOf(2, 1, 3)).at(30_000_000))
check(261214 == Game(listOf(1, 2, 3)).at(30_000_000))
check(6895259 == Game(listOf(2, 3, 1)).at(30_000_000))
check(18 == Game(listOf(3, 2, 1)).at(30_000_000))
check(362 == Game(listOf(3, 1, 2)).at(30_000_000))

check(48710 == Game(puzzleInput).at(30_000_000))