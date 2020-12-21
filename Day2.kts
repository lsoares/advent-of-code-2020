fun checkValidity(val input: List<Pair<Policy, String>>) =
    input.count { it.first.validate(it.second) }

data class Policy(val min: Int, val max: Int, val letter: Char) {
    fun validate(password: String) = password.count { letter } in min..max
}

check(
    2 == checkValidity(
        listOf(
            Policy(1, 3, 'a') to "abcde",
            Policy(1, 3, 'b') to "cdefg",
            Policy(2, 9, 'c') to "ccccccccc",
        )
    )
)
