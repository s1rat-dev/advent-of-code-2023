import java.io.File
import kotlin.math.pow

fun main() {
    val path = "src/main/kotlin/inputs/Day07.txt"
    val file = File(path).absoluteFile
    val game = mutableMapOf<String, Int>()

    file.forEachLine { line ->
        line.split(" ").let { game[it.first()] = it.last().toInt() }
    }

    fun List<Pair<List<Int>, List<Pair<String, Map<Char, List<Char>>>>>>.getGameScore(values: Map<Char,Int>): Int {
        val results = mutableMapOf<String, Int>()

        this.forEach { pair ->
            val map = mutableMapOf<String, Int>()
            for (i in 0..<5) {
                pair.second.forEach {
                    map[it.first] = map.getOrDefault(it.first, 0).plus(values[it.first[i]]!! * 14.0.pow(5 - i).toInt())
                }
                if (map.values.sum() == map.values.toSet().sum()) {
                    results.putAll(map.toList().sortedBy { it.second })
                    break
                }
            }

        }

        return results.toList().mapIndexed { index, pair -> (index + 1) * game[pair.first]!! }.sum()
    }

    fun part1(): Int {
        val values = mapOf(
            'A' to 14,
            'K' to 13,
            'Q' to 12,
            'J' to 11,
            'T' to 10,
            '9' to 9,
            '8' to 8,
            '7' to 7,
            '6' to 6,
            '5' to 5,
            '4' to 4,
            '3' to 3,
            '2' to 2
        )

        val gameSet = game.toList().map { pair -> pair.first to pair.first.groupBy { it } }
            .groupBy { it.second.values.map { it.size }.sortedDescending() }.toList()
            .sortedBy { it.first.size }.reversed()

       return gameSet.getGameScore(values)
    }


    fun part2(): Int {
        val values = mapOf(
            'A' to 14,
            'K' to 13,
            'Q' to 12,
            'T' to 11,
            '9' to 10,
            '8' to 9,
            '7' to 8,
            '6' to 7,
            '5' to 6,
            '4' to 5,
            '3' to 4,
            '2' to 3,
            'J' to 2,
        )

        val newGame = game.map {
            val count = it.key.replace("J","").groupingBy { it }.eachCount()
            val max = count.values.maxOrNull() ?: return@map "AAAAA" to it.value
            val maxChar = count.filter { it.value == max }
                .map { it.key to values[it.key]!! }
                .maxBy { it.second }.first
            it.key.replace('J',maxChar) to it.value
        }

        val gameSet = game.toList().mapIndexed { index, pair -> pair.first to newGame[index].first.groupBy { it } }
            .groupBy { it.second.values.map { it.size }.sortedDescending() }.toList()
            .sortedBy { it.first.size }.reversed()

        return gameSet.getGameScore(values)
    }

    println(part1())
    println(part2())
}
