import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths
import java.util.*

// part 1
fun toPassport(input: String) = input
    .split("\\s".toRegex())
    .map { it.split(":").let { it[0] to it[1] } }
    .toMap()

val sampleInput = sequenceOf(
    "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd byr:1937 iyr:2017 cid:147 hgt:183cm",
    "iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884 hcl:#cfa07d byr:1929",
    "hcl:#ae17e1 iyr:2013 eyr:2024 ecl:brn pid:760753108 byr:1931 hgt:179cm",
    "hcl:#cfa07d eyr:2025 pid:166559648 iyr:2011 ecl:brn hgt:59in"
).map(::toPassport)

check(2 == countValidPassports1(sampleInput))

fun countValidPassports1(input: Sequence<Map<String, String>>) = input.count(::isValidPassport1)

fun isValidPassport1(passport: Map<String, String>) =
    setOf("ecl", "pid", "eyr", "hcl", "byr", "iyr", "hgt").all(passport::containsKey)

val path = "${Paths.get("").toAbsolutePath()}/input/4.txt"
val input = Scanner(FileInputStream(File(path))).useDelimiter("\n\n").asSequence().map(::toPassport)
println(countValidPassports1(input))

// part 2
val sampleInput2 = sequenceOf(
    "eyr:1972 cid:100 hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926",
    "iyr:2019 hcl:#602927 eyr:1967 hgt:170cm ecl:grn pid:012533040 byr:1946",
    "hcl:dab227 iyr:2012 ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277",
    "hgt:59cm ecl:zzz eyr:2038 hcl:74454a iyr:2023 pid:3556412378 byr:2007",
    "pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980 hcl:#623a2f",
    "eyr:2029 ecl:blu cid:129 byr:1989 iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm",
    "hcl:#888785 hgt:164cm byr:2001 iyr:2015 cid:88 pid:545766238 ecl:hzl eyr:2022",
    "iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719"""
).map(::toPassport)

fun countValidPassports2(input: Sequence<Map<String, String>>) = input.count(::isValidPassport2)

check(4 == countValidPassports2(sampleInput2))

fun isValidPassport2(passport: Map<String, String>) = with(passport) {
    get("byr")?.let { it.length == 4 && it.toInt() in 1920..2002 } == true &&
            get("iyr")?.let { it.length == 4 && it.toInt() in 2010..2020 } == true &&
            get("eyr")?.let { it.length == 4 && it.toInt() in 2020..2030 } == true &&
            get("hgt")?.let {
                it.endsWith("cm") && it.removeSuffix("cm").let { it.all(Char::isDigit) && it.toInt() in 150..193 } ||
                        it.endsWith("in") && it.removeSuffix("in").let { it.all(Char::isDigit) && it.toInt() in 59..76 }
            } == true &&
            get("hcl")?.let { it.startsWith("#") && it.drop(1).toLongOrNull(16) != null } == true &&
            get("ecl") in setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth") &&
            get("pid")?.let { it.length == 9 && it.all(Char::isDigit) } == true
}

val input2 = Scanner(FileInputStream(File(path))).useDelimiter("\n\n").asSequence().map(::toPassport)
println(countValidPassports2(input2))
