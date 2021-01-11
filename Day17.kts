data class Point(val x: Int, val y: Int, val z: Int) {
    fun move(move: Triple<Int, Int, Int>) = copy(x + move.first, y + move.second, z + move.third)
}

data class Cubes(val points: List<List<String>>) {

    fun iterate() = points
        .mapNotNull { it.first to nextAt(it) }.toSet()
        .let(::Cubes)

    fun nextAt(point: Point) = when (at(point)) {
        ACTIVE -> when (around(point).count { it == ACTIVE }) {
            2, 3 -> ACTIVE
            else -> null
        }
        else -> when (around(point).count { it == ACTIVE }) {
            3 -> ACTIVE
            else -> null
        }
    }
//    If a cube is active and exactly 2 or 3 of its neighbors are also active, the cube remains active. Otherwise, the cube becomes inactive.
//    If a cube is inactive but exactly 3 of its neighbors are active, the cube becomes active. Otherwise, the cube remains inactive.

    fun at(point: Point) = points.getOrNull(seat.y)?.getOrNull(seat.x)

    fun around(point: Point) = directions.map { at(point.move(it)) }

    companion object {
        val directions = (-1..1).flatMap { z ->
            (-1..1).flatMap { y ->
                (-1..1).map { x -> Triple(x, y, z) }
            }
        }
        private val ACTIVE = '#'
    }
}

val cubes = Cubes(
    setOf(
        Point(0, 0, 0) to '#',
        Point(0, 0, -1) to '#',
        Point(0, 0, 1) to '#',
    )
)
println(cubes.nextAt(Point(0, 1, 0) to '.'))
/*
z1            z2           z3              |y1
x1 x2 x3      x1 x2 x3     x1 x2 x3        |y2
x1 x2 x3      x1 x2 x3     x1 x2 x3        |y3
 */