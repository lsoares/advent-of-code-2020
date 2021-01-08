import Day12.Instruction.Action
import Day12.Instruction.Action.*
import Day12.Ship.MoveMode.SHIP
import Day12.Ship.MoveMode.WAYPOINT
import java.io.File
import java.io.FileInputStream
import java.lang.Math.toRadians
import java.nio.file.Paths
import java.util.*
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

// --- Part One ---
val sampleInput = sequenceOf("F10", "N3", "F7", "R90", "F11").map(::toInstruction)
fun ship1() = Ship(position = Position(0, 0), moveMode = SHIP, waypoint = Position(1, 0))

check(25 == ship1().execute(sampleInput).position.manhattanDistance)

fun toInstruction(cmd: String) = Instruction(
    action = cmd.first().let { letter -> Action.values().first() { it.name.startsWith(letter) } },
    value = cmd.drop(1).toInt()
)

data class Instruction(val action: Action, val value: Int) {
    enum class Action { NORTH, SOUTH, EAST, WEST, LEFT, RIGHT, FORWARD }
}

data class Position(val x: Int, val y: Int) {
    fun move(xInc: Int, yInc: Int) = Position(x + xInc, y + yInc)

    fun rotate(angle: Int) = Position(
        x = x * cos(-angle.asRad()).toInt() - y * sin(-angle.asRad()).toInt(),
        y = x * sin(-angle.asRad()).toInt() + y * cos(-angle.asRad()).toInt(),
    )

    private fun Int.asRad() = toRadians(toDouble())

    val manhattanDistance get() = abs(x) + abs(y)
}

data class Ship(val position: Position, val waypoint: Position, val moveMode: MoveMode) {

    enum class MoveMode { SHIP, WAYPOINT }

    fun execute(instructions: Sequence<Instruction>) =
        instructions.fold(this) { state, command ->
            state.execute(command)
        }

    private fun execute(instruction: Instruction) =
        when (instruction.action) {
            NORTH -> move(yInc = instruction.value)
            SOUTH -> move(yInc = -instruction.value)
            EAST -> move(xInc = instruction.value)
            WEST -> move(xInc = -instruction.value)
            RIGHT -> copy(waypoint = waypoint.rotate(instruction.value))
            LEFT -> copy(waypoint = waypoint.rotate(-instruction.value))
            FORWARD -> copy(
                position = position.move(xInc = waypoint.x * instruction.value, yInc = waypoint.y * instruction.value)
            )
        }

    private fun move(xInc: Int = 0, yInc: Int = 0) =
        when (moveMode) {
            SHIP -> copy(position = position.move(xInc, yInc))
            WAYPOINT -> copy(waypoint = waypoint.move(xInc, yInc))
        }
}

fun getPuzzleInput() =
    Scanner(FileInputStream(File("${Paths.get("").toAbsolutePath()}/input/12.txt")))
        .asSequence().map(::toInstruction)

check(420 == ship1().execute(getPuzzleInput()).position.manhattanDistance)

// --- Part Two ---
fun ship2() = Ship(position = Position(0, 0), moveMode = WAYPOINT, waypoint = Position(10, 1))

check(286 == ship2().execute(sampleInput).position.manhattanDistance)
check(42073 == ship2().execute(getPuzzleInput()).position.manhattanDistance)
