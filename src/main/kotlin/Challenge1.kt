object Challenge1 {

    fun solve(input: List<Int>) = input
        .cartesianProduct(input)
        .first { it.first + it.second == 2020 }
        .let { it.first * it.second }

    private fun <S> List<S>.cartesianProduct(other: List<S>): List<Pair<S, S>> =
        flatMap { List(other.size) { i -> Pair(it, other[i]) } }

    fun solveTriple(input: List<Int>) = input
        .cartesianProduct(input)
        .flatMap { pair -> List(input.size) { Triple(pair.first, pair.second, input[it]) } }
        .first { it.first + it.second + it.third == 2020 }
        .let { it.first * it.second * it.third }
}
