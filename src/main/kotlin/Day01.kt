import java.io.File
import java.lang.StringBuilder

fun main() {

    val path = "src/main/kotlin/inputs/Day01.txt"
    val file = File(path).absoluteFile
    val numbers = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )

    fun part1(): Int = buildList {
        file.forEachLine { line ->
            line.filter { it.isDigit() }.apply {
                add("${first()}${last()}".toInt())
            }
        }
    }.sum()


    fun part2(): Int = buildList {
        file.forEachLine { line ->
            val firstValues = StringBuilder()
            val lastValues = StringBuilder()

            val remainFirst = line.dropWhile { ch ->
                firstValues.append(ch)
                !ch.isDigit() && numbers.keys.none { firstValues.contains(it) }
            }

            val remainLast = line.dropLastWhile { ch ->
                lastValues.append(ch)
                !ch.isDigit() && numbers.keys.none { lastValues.reversed().contains(it) }
            }

            val firstNum = numbers[numbers.keys.find { firstValues.contains(it) }] ?: remainFirst.first()
            val lastNum = numbers[numbers.keys.find { lastValues.reversed().contains(it) }] ?: remainLast.last()

            add("${firstNum}${lastNum}".toInt())
        }
    }.sum()


    println(part1())
    println(part2())
}