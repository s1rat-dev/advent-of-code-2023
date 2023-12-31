import java.io.File
import java.math.BigDecimal

fun main() {
    val path = "src/main/kotlin/inputs/Day08.txt"
    val file = File(path).absoluteFile

    val navigates = file.readLines().first().map { if (it == 'L') 0 else 1 }
    val map = file.readLines().subList(2, 768).map {
        with(it.split('=')) {
            first().trim() to last().split(',').map { str ->
                str.filter { it.isLetter() }
            }
        }
    }.toMap()

    fun String.getLocationCount(body: (location: String) -> Boolean): Int {
        var location = this
        var count = 0
        var navigateCopy = navigates.toMutableList()

        while (navigateCopy.size > 0) {
            val route = navigateCopy.removeFirst()
            location = map[location]!![route]
            if (navigateCopy.size == 0 && body.invoke(location))
                navigateCopy = navigates.toMutableList()

            count++
        }
        return count
    }

    fun part1(): Int = "AAA".getLocationCount { location -> location != "ZZZ" }

    fun part2(): BigDecimal {
        val locations = map.keys.filter { it.last() == 'A' }

        var counts = locations.map {
            BigDecimal(it.getLocationCount { location -> location.last() != 'Z' })
        }.toMutableList()

        var currentSize = 0
        while (counts.size != currentSize) {
            currentSize = counts.size
            Util.findTriples(counts).forEach {
                counts = (counts - listOf(it.first, it.second)).toMutableList()
                counts.add(it.first * it.second / it.third)
            }
        }

        return counts.reduce(BigDecimal::multiply)
    }

    println(part1())
    println(part2())
}


class Util {
    companion object {
        fun findTriples(numbers: List<BigDecimal>): List<Triple<BigDecimal, BigDecimal, BigDecimal>> {
            val result = mutableListOf<Triple<BigDecimal, BigDecimal, BigDecimal>>()

            for (i in numbers.indices) {
                for (j in i + 1 until numbers.size) {
                    val gcd = gcd(numbers[i], numbers[j])
                    if (gcd != BigDecimal.ONE && !result.any { it.third == gcd }) {
                        result.add(Triple(numbers[i], numbers[j], gcd))
                    }
                }
            }

            return result
        }

        private fun gcd(a: BigDecimal, b: BigDecimal): BigDecimal {
            var x = a
            var y = b
            while (y != BigDecimal.ZERO) {
                val temp = y
                y = x % y
                x = temp
            }
            return x
        }
    }
}