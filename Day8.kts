import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths
import java.util.*

// --- Part One ---
val sampleInput = listOf(
    "nop +0",
    "acc +1",
    "jmp +4",
    "acc +3",
    "jmp -3",
    "acc -99",
    "acc +1",
    "jmp -4",
    "acc +6",
).map(::toInstruction)

fun toInstruction(str: String) = str.split(" ").let { Instruction(it.first(), it.last().toInt()) }

check(5 == process(sampleInput))

data class Instruction(val operation: String, val value: Int)
data class State(val current: Int = 0, val visited: List<Int> = emptyList(), val accumulator: Int = 0)

fun process(instructions: List<Instruction>, state: State = State()): Int =
    instructions[state.current].let { instruction ->
        if (state.visited.contains(state.current)) return@let state.accumulator

        val newState = when (instruction.operation) {
            "nop" -> state.copy(current = state.current + 1)
            "acc" -> state.copy(current = state.current + 1, accumulator = state.accumulator + instruction.value)
            "jmp" -> state.copy(current = state.current + instruction.value)
            else -> error("unknown instruction")
        }.copy(visited = state.visited + state.current)
        process(instructions, newState)
    }

val path = "${Paths.get("").toAbsolutePath()}/input/8.txt"
val input = Scanner(FileInputStream(File(path))).useDelimiter("\n")
    .asSequence().map(::toInstruction).toList()

println(process(input))
