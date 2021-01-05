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

data class BitMask(val mask: String = "X".repeat(36)) {
    private val andMask = mask.replace("X", "1").toLong(2)
    private val orMask = mask.replace("X", "0").toLong(2)

    fun applyTo(value: Long) = value and andMask or orMask
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

val path = "${Paths.get("").toAbsolutePath()}/input/14.txt"
val input = Scanner(FileInputStream(File(path))).useDelimiter(System.lineSeparator()).asSequence()
    .map(::parse)

check(12610010960049L == process(input).sumAll())

// --- Part Two ---

data class Memory2(private val mem: Map<Long, Long> = emptyMap(), val bitMask: BitMask2 = BitMask2()) {
    fun set(address: Long, value: Long) =
        copy(mem = bitMask.applyTo(address).fold(mem) { memEach, addressEach ->
            memEach + (addressEach to value)
        })

    fun sumAll() = mem.values.sum()
}

data class BitMask2(val mask: String = "0".repeat(36)) {
    private val orMask = mask.replace("X", "0").toLong(2)

    private val combinations by {
        val maxComb = pow(2.0, mask.count { it == 'X' }.toDouble()).toInt()
        (0 until maxComb).map {
            it.toString(2).padStart(mask.count { it == 'X' }, '0')
        }.map(::applyCombination)
    }

    private fun applyCombination(string: String): String {
        val m = mask.toMutableList()
        var curr = 0
        m.forEachIndexed { index: Int, c: Char ->
            if (c == 'X') {
                m[index] = string[curr]
                curr++
            }
        }
        return m.joinToString("")
    }

    fun applyTo(address: Long): List<Long> = combinations.
//    listOf(address or orMask)
}

fun process2(instructions: Sequence<Instruction>) = instructions
    .fold(Memory2()) { memory, (type, arg0, arg1) ->
        when (type) {
            SET_MASK -> memory.copy(bitMask = BitMask2(arg0))
            SET_VALUE -> memory.set(arg0.toLong(), arg1?.toLong() ?: error("missing arg"))
        }
    }

val sampleInput2 = sequenceOf(
//    "mask = 000000000000000000000000000000X1001X",
    //"mem[42] = 100",
    "mask = 00000000000000000000000000000000X0XX",
    //"mem[26] = 1",
).map(::parse)

println(process2(sampleInput2))