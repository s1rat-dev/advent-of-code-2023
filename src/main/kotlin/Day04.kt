import java.io.File
import kotlin.math.pow

fun main() {
    val path = "src/main/kotlin/inputs/Day04.txt"
    val file = File(path).absoluteFile

    val scratchCardPoints = mutableMapOf<Int, Int>()


    fun String.getAsListWithoutBlank(): Set<String> {
        return this.split(' ').filter { it.isNotEmpty() }.toSet()
    }

    fun List<String>.getMatchingCount(): Int {
        val winnerNumbers = first().substringAfter(':').trim()
        val myNumbers = last().trim()

        val myNumbersAsList = myNumbers.getAsListWithoutBlank()
        val differenceSize = myNumbersAsList.minus(winnerNumbers.getAsListWithoutBlank()).size

        return myNumbersAsList.size - differenceSize

    }

    fun increaseGameNumber(gameNumber: Int) {
        scratchCardPoints[gameNumber] = scratchCardPoints.getOrDefault(gameNumber, 0 ) + 1
    }

    fun part1(): Double {
        var point = 0.0

        file.forEachLine {
            it.split('|').apply {
                (this.getMatchingCount()).takeIf { it != 0 }?.let {
                    point += 2.0.pow((it - 1).toDouble())
                }
            }
        }

        return point
    }

    fun part2(): Int {
        file.forEachLine {
            it.split('|').apply {
                val gameNumber = this.first().substringBefore(':').split(' ').last().toInt()
                val matchingCount = this.getMatchingCount()

                increaseGameNumber(gameNumber)
                repeat(scratchCardPoints[gameNumber]!!) {
                    for (i in 1..matchingCount) {
                        increaseGameNumber(gameNumber + i)
                    }
                }
            }
        }
        return scratchCardPoints.values.sum()
    }

    println(part1())
    println(part2())


}