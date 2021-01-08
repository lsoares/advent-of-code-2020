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

check(5 == findLoop(sampleInput).accumulator)

data class Instruction(val operation: String, val value: Int)
data class State(
    val current: Int = 0,
    val accumulator: Int = 0,
    val visited: Set<Int> = emptySet(),
    val loopFound: Boolean = false
)

tailrec fun findLoop(instructions: List<Instruction>, state: State = State()): State {
    if (state.current in state.visited) return state.copy(loopFound = true)

    val newState = process(instructions[state.current], state)
    return when (newState.current) {
        instructions.size -> newState
        else -> findLoop(instructions, newState)
    }
}

fun process(instruction: Instruction, state: State) = with(state) {
    when (instruction.operation) {
        "nop" -> copy(current = current + 1)
        "acc" -> copy(current = current + 1, accumulator = accumulator + instruction.value)
        "jmp" -> copy(current = current + instruction.value)
        else -> error("unknown instruction")
    }.copy(visited = visited + current)
}

val path = "${Paths.get("").toAbsolutePath()}/input/8.txt"
val puzzleInput = Scanner(FileInputStream(File(path))).useDelimiter(System.lineSeparator())
    .asSequence().map(::toInstruction).toList()

check(1867 == findLoop(puzzleInput).accumulator)

// --- Part Two ---
check(8 == firstWithoutLoop(sampleInput).accumulator)

fun firstWithoutLoop(instructions: List<Instruction>) = instructions
    .asSequence()
    .mapIndexedNotNull { index, instruction ->
        instructions.run {
            when (instruction.operation) {
                "jmp" -> replacing(instruction.copy("nop"), index)
                "nop" -> replacing(instruction.copy("jmp"), index)
                else -> null
            }
        }
    }
    .map(::findLoop)
    .first { !it.loopFound }

fun <T> List<T>.replacing(newEl: T, index: Int) =
    subList(0, index) + newEl + subList(index + 1, size)

check(1303 == firstWithoutLoop(puzzleInput).accumulator)