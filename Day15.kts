// --- Part One ---
check(0 == Game(listOf(0, 3, 6)).drop(9).first())

data class Game(private val startingNumbers: List<Int>) : Sequence<Int> {
    private val usedNumbers = mutableListOf<Int>()

    override fun iterator() =
        object : Iterator<Int> {
            override fun hasNext() = true
            override fun next() = calc().also(usedNumbers::add)
        }

    private fun calc(): Int {
        if (usedNumbers.size < startingNumbers.size) {
            return startingNumbers[usedNumbers.size]
        }
        val lastNumberUsed = usedNumbers
            .withIndex()
            .filter { it.value == usedNumbers.last() }

        if (lastNumberUsed.size == 1) {
            return 0
        }
        return lastNumberUsed.last().index - lastNumberUsed.dropLast(1).last().index
    }
}

check(1194 == Game(listOf(6, 13, 1, 15, 2, 0)).drop(2019).first())
