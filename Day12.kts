import Day12.Command.Action
import Day12.Command.Action.*
import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths
import java.util.*
import kotlin.math.abs

// --- Part One ---
val sampleInput = sequenceOf("F10", "N3", "F7", "R90", "F11").map(::toCommand)

fun toCommand(cmd: String) = Command(
    action = cmd.first().let { letter -> Action.values().first() { it.name.startsWith(letter) } },
    value = cmd.drop(1).toInt()
)

data class Command(val action: Action, val value: Int) {
    enum class Action { NORTH, SOUTH, EAST, WEST, LEFT, RIGHT, FORWARD }
}

data class State(val x: Int, val y: Int, val direction: Direction) {
    val manhattanDistance = abs(x) + abs(y)

    fun run(command: Command): State =
        when (command.action) {
            NORTH -> copy(y = y + command.value)
            EAST -> copy(x = x + command.value)
            SOUTH -> copy(y = y - command.value)
            WEST -> copy(x = x - command.value)
            RIGHT -> copy(direction = newDirection(command.value))
            LEFT -> copy(direction = newDirection(360 - command.value))
            FORWARD -> run(command.copy(valueOf(direction.name)))
        }

    private fun newDirection(delta: Int) =
        Direction.values().first { it.degrees == (direction.degrees + delta) % 360 }

    enum class Direction(val degrees: Int) {
        NORTH(0), EAST(90), SOUTH(180), WEST(270)
    }
}

fun process(commands: Sequence<Command>) =
    commands.fold(State(0, 0, State.Direction.EAST)) { state, command ->
        state.run(command)
    }

check(25 == process(sampleInput).manhattanDistance)

val path = "${Paths.get("").toAbsolutePath()}/input/12.txt"
val input = Scanner(FileInputStream(File(path))).asSequence().map(::toCommand)

println(process(input).manhattanDistance) // 420