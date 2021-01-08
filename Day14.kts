import Day14.Program.Instruction
import Day14.Program.Instruction.Type.SET_MASK
import Day14.Program.Instruction.Type.SET_VALUE
import java.io.File
import java.io.FileInputStream
import java.lang.Math.pow
import java.nio.file.Paths
import java.util.*

// --- Part One ---
val sampleInput1 = sequenceOf(
    "mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X",
    "mem[8] = 11",
    "mem[7] = 101",
    "mem[8] = 0",
).map(::parse)

fun parse(instructionStr: String) = instructionStr.let {
    if (it.startsWith("mem[")) {
        Instruction(
            type = SET_VALUE,
            arg0 = it.substringAfter("[").substringBefore("]"),
            arg1 = it.substringAfter("= "),
        )
    } else Instruction(
        type = SET_MASK,
        arg0 = it.substringAfter("= ")
    )
}

check(165L == process(sampleInput1).sumAll())

data class Program(val instructions: Sequence<Instruction>) {
    data class Instruction(val type: Type, val arg0: String, val arg1: String? = null) {
        enum class Type { SET_VALUE, SET_MASK }
    }
}

data class BitMask(val value: String = "X".repeat(36)) {
    private val andMask = value.replace("X", "1").toLong(2)
    private val orMask = value.replace("X", "0").toLong(2)

    fun applyTo(number: Long) = number and andMask or orMask
}

data class Memory(private val mem: Map<Int, Long> = emptyMap(), val bitMask: BitMask = BitMask()) {
    fun set(address: Int, value: Long) =
        copy(mem = mem + (address to bitMask.applyTo(value)))

    fun sumAll() = mem.values.sum()
}

fun process(instructions: Sequence<Instruction>) = instructions
    .fold(Memory()) { memory, (type, arg0, arg1) ->
        when (type) {
            SET_MASK -> memory.copy(bitMask = BitMask(arg0))
            SET_VALUE -> memory.set(arg0.toInt(), arg1?.toLong() ?: error("missing arg"))
        }
    }

fun getPuzzleInput() = Scanner(FileInputStream(File("${Paths.get("").toAbsolutePath()}/input/14.txt")))
    .useDelimiter(System.lineSeparator()).asSequence()
    .map(::parse)

check(12610010960049L == process(getPuzzleInput()).sumAll())

// --- Part Two ---
val sampleInput2 = sequenceOf(
    "mask = 000000000000000000000000000000X1001X",
    "mem[42] = 100",
    "mask = 00000000000000000000000000000000X0XX",
    "mem[26] = 1",
).map(::parse)
check(208L == process2(sampleInput2).sumAll())

data class Memory2(private val mem: MutableMap<Long, Long> = mutableMapOf(), var bitMask: BitMask2 = BitMask2()) {
    fun set(address: Long, value: Long) =
        bitMask.applyTo(address).forEach { l: Long ->
            mem[l] = value
        }

    fun sumAll() = mem.values.sum()
}

data class BitMask2(val value: String = "0".repeat(36)) {
    private val xCount = value.count { it == 'X' }

    fun applyTo(address: Long): Sequence<Long> =
        (0 until pow(2.0, xCount.toDouble()).toInt())
            .asSequence()
            .map { it.toString(2).padStart(xCount, '0') }
            .map {
                it.fold(prepareNewMask(address)) { acc, ch ->
                    acc.replaceFirst('X', ch)
                }.toLong(2)
            }

    private fun prepareNewMask(address: Long) = address
        .toString(2)
        .padStart(36, '0')
        .withIndex()
        .filter { it.value == '1' }
        .fold(value) { acc, (index, value) ->
            if (acc[index] != 'X') acc.replaceRange(index, index + 1, value.toString())
            else acc
        }
}

fun process2(instructions: Sequence<Instruction>): Memory2 {
    val memory = Memory2()
    instructions.forEach { (type, arg0, arg1) ->
        when (type) {
            SET_MASK -> memory.bitMask = BitMask2(arg0)
            SET_VALUE -> memory.set(arg0.toLong(), arg1?.toLong() ?: error("missing arg"))
        }
    }
    return memory
}

check(3608464522781L == process2(getPuzzleInput()).sumAll())