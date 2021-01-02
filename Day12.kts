import Day12.Instruction.Action
import Day12.Instruction.Action.*
import Day12.Ship.MoveMode.SHIP
import Day12.Ship.MoveMode.WAYPOINT
import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths
import java.util.*
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

// --- Part One ---
val sampleInput = sequenceOf("F10", "N3", "F7", "R90", "F11").map(::toCommand)

check(25 == Ship().execute(sampleInput).position.manhattanDistance())

fun toCommand(cmd: String) = Instruction(
    action = cmd.first().let { letter -> Action.values().first() { it.name.startsWith(letter) } },
    value = cmd.drop(1).toInt()
)

data class Instruction(val action: Action, val value: Int) {
    enum class Action { NORTH, SOUTH, EAST, WEST, LEFT, RIGHT, FORWARD }
}

data class Position(val x: Int, val y: Int) {
    fun move(xInc: Int = 0, yInc: Int = 0) = Position(x + xInc, y + yInc)

    fun rotate(angle: Int) = Position(
        x = x * cos(-angle.asRad()).toInt() - y * sin(-angle.asRad()).toInt(),
        y = x * sin(-angle.asRad()).toInt() + y * cos(-angle.asRad()).toInt(),
    )

    private fun Int.asRad() = Math.toRadians(toDouble())

    fun manhattanDistance() = abs(x) + abs(y)
}

enum class Direction { NORTH, EAST, SOUTH, WEST }

data class Ship(
    val position: Position = Position(0, 0),
    val waypoint: Position = Position(1, 0),
    val moveMode: MoveMode = SHIP,
) {

    enum class MoveMode { SHIP, WAYPOINT }

    fun execute(commands: Sequence<Instruction>) =
        commands.fold(this) { state, command ->
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

fun loadFile() =
    Scanner(FileInputStream(File("${Paths.get("").toAbsolutePath()}/input/12.txt")))
        .asSequence().map(::toCommand)

check(420 == Ship().execute(loadFile()).position.manhattanDistance())

// --- Part Two ---
check(
    286 == Ship(moveMode = WAYPOINT, waypoint = Position(10, 1))
        .execute(sampleInput).position.manhattanDistance()
)

check(
    42073 == Ship(moveMode = WAYPOINT, waypoint = Position(10, 1))
        .execute(loadFile()).position.manhattanDistance()
)