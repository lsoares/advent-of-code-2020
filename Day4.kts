import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths
import java.util.*

val sampleInput = """
ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
byr:1937 iyr:2017 cid:147 hgt:183cm

iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884
hcl:#cfa07d byr:1929

hcl:#ae17e1 iyr:2013
eyr:2024
ecl:brn pid:760753108 byr:1931
hgt:179cm

hcl:#cfa07d eyr:2025 pid:166559648
iyr:2011 ecl:brn hgt:59in""".trim().split("\n\n").map(::toPassport)

fun toPassport(input: String) = input
    .split("\\s".toRegex())
    .map { it.split(":").let { it[0] to it[1] } }
    .toMap()

check(2 == countValidPassports(sampleInput))

fun countValidPassports(input: List<Map<String, String>>) = input.count(::isValidPassport)

fun isValidPassport(passport: Map<String, String>) =
    setOf("ecl", "pid", "eyr", "hcl", "byr", "iyr", "hgt").all(passport::containsKey)

val path = "${Paths.get("").toAbsolutePath()}/input/4.txt"
val input = Scanner(FileInputStream(File(path))).useDelimiter("\n\n").asSequence().toList().map(::toPassport)

println(countValidPassports(input))