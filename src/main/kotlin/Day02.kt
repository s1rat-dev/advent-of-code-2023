import java.io.File

fun main() {
    val path = "src/main/kotlin/inputs/Day02.txt"
    val file = File(path).absoluteFile
    val minimumValues = mutableMapOf(
        "red" to 0,
        "green" to 0,
        "blue" to 0
    )
    val possibleGameValues = mutableMapOf(
        "red" to 12,
        "green" to 13,
        "blue" to 14
    )

    fun String.withGameInfo(body: (List<String>) -> Unit) {
        this.substringAfter(':').trim().split(';').forEach { game ->
            game.split(',').forEach {
                it.trim().split(" ").apply {
                    body.invoke(this)
                }
            }
        }
    }

    fun part1(): Int {
        var point = 0

        file.forEachLine { line ->
            var eliminated = false
            val gameId = line.substringBefore(':').takeLastWhile { it.isDigit() }.toInt()

            line.withGameInfo {
                if (it.first().toInt() > possibleGameValues[it.last()]!!) eliminated = true
            }

            if (!eliminated) point += gameId
        }
        return point
    }

    fun part2(): Int {
        var point = 0

        file.forEachLine { line ->

            line.withGameInfo {
                if (it.first().toInt() > minimumValues[it.last()]!!) {
                    minimumValues[it.last()] = it.first().toInt()
                }
            }

            point += minimumValues.values.reduce(Int::times)
        }
        return point
    }


    println(part1())
    println(part2())

}