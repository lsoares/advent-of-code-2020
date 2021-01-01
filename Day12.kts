import Day12.Instruction.Action
import Day12.Instruction.Action.*
import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths
import java.util.*
import kotlin.math.abs

// --- Part One ---
val sampleInput = sequenceOf("F10", "N3", "F7", "R90", "F11").map(::toCommand)

check(25 == Ship().execute1(sampleInput).position.manhattanDistance())

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

    fun manhattanDistance(from: Position = Position(0, 0)) =
        abs(x - from.x) + abs(y - from.y)
}

enum class Direction(val degrees: Int) {
    NORTH(0), EAST(90), SOUTH(180), WEST(270);

    fun update(delta: Int) =
        Direction.values().first { it.degrees == (degrees + delta) % 360 }
}

data class Ship(
    val position: Position = Position(0, 0),
    val direction: Direction = Direction.EAST,
) {

    fun execute1(commands: Sequence<Instruction>) =
        commands.fold(this) { state, command ->
            state.execute1(command)
        }

    private fun execute1(instruction: Instruction): Ship =
        when (instruction.action) {
            NORTH, EAST, SOUTH, WEST -> copy(
                position = position.move(Direction.valueOf(instruction.action.name), instruction.value)
            )
            RIGHT -> copy(direction = direction.update(instruction.value))
            LEFT -> copy(direction = direction.update(360 - instruction.value))
            FORWARD -> execute1(instruction.copy(action = Action.valueOf(direction.name)))
        }
}

fun loadFile() =
    Scanner(FileInputStream(File("${Paths.get("").toAbsolutePath()}/input/12.txt")))
        .asSequence().map(::toCommand)

check(420 == Ship().execute1(loadFile()).position.manhattanDistance())
