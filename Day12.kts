import Day12.Instruction.Action
import Day12.Instruction.Action.*
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
    fun move(direction: Direction, amount: Int) =
        when (direction) {
            Direction.NORTH -> copy(y = y + amount)
            Direction.EAST -> copy(x = x + amount)
            Direction.SOUTH -> copy(y = y - amount)
            Direction.WEST -> copy(x = x - amount)
        }

    fun rotate(angle: Int) = Position(
        x = x * cos(angle.asRad()).toInt() - y * sin(angle.asRad()).toInt(),
        y = x * sin(angle.asRad()).toInt() + y * cos(angle.asRad()).toInt(),
    )

    private fun Int.asRad() = Math.toRadians(toDouble())

    fun manhattanDistance() = abs(x) + abs(y)
}

enum class Direction { NORTH, EAST, SOUTH, WEST }

data class Ship(
    val position: Position = Position(0, 0),
    val waypoint: Position = Position(1, 0),
) {

    fun execute(commands: Sequence<Instruction>) =
        commands.fold(this) { state, command ->
            state.execute(command)
        }

    private fun execute(instruction: Instruction) =
        when (instruction.action) {
            NORTH, EAST, SOUTH, WEST -> copy(
                position = position.move(Direction.valueOf(instruction.action.name), instruction.value)
            )
            RIGHT, LEFT -> copy(
                waypoint = waypoint.rotate(instruction.value * (if (instruction.action == LEFT) 1 else -1))
            )
            FORWARD -> copy(
                position = Position(
                    x = position.x + waypoint.x * instruction.value,
                    y = position.y + waypoint.y * instruction.value,
                )
            )
        }
}

fun loadFile() =
    Scanner(FileInputStream(File("${Paths.get("").toAbsolutePath()}/input/12.txt")))
        .asSequence().map(::toCommand)

check(420 == Ship().execute(loadFile()).position.manhattanDistance())

// --- Part Two ---
//check(286 == Ship().execute(sampleInput).position.manhattanDistance())
