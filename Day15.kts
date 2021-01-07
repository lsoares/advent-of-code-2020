// --- Part One ---
check(0 == Game(listOf(0, 3, 6)).at(10))

data class Game(private val startingNumbers: List<Int>) {
    private val usedNumbers = mutableMapOf<Int, Int>()
    private var size = 0
    private var lastUsed = - 1

    init {
        startingNumbers.forEach(::store)
    }

    fun at(nth: Int): Int {
        repeat(nth) {
            store(calc())
        }
        return lastUsed
    }

    private fun calc(): Int {
        val referencesToLast = usedNumbers.getOrElse(lastUsed, { return 0 })

        return size - referencesToLast
    }

    private fun store(number: Int) {
        usedNumbers[number] = size
        lastUsed = number
        size ++
    }
}

//If that was the first time the number has been spoken, the current player says 0.
//Otherwise, the number had been spoken before; the current player announces how many turns apart the number is from when
// it was previously spoken.

check(1194 == Game(listOf(6, 13, 1, 15, 2, 0)).at(2020))

// --- Part Two ---
check(175594 == Game(listOf(0, 3, 6)).at(30_000_000))