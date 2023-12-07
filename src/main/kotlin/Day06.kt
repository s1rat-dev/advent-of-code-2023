import java.io.File

fun main() {
    val path = "src/main/kotlin/inputs/Day06.txt"
    val file = File(path).absoluteFile

    fun getLineValues(index: Int) =
        file.readLines().get(index).split(":").last().trim().split(" ")
            .filter { it.isNotBlank() }
            .map { it.toLong() }

    fun List<Long>.joinValues() = this.joinToString("").toLong()

    fun Map<Long,Long>.getPossibilities(): MutableList<Long> {
        val possibilities = MutableList(this.size) { 1L }
        this.onEachIndexed { index, (ms, distance) ->
            for (i in 0..ms) {
                if (i * (ms - i) > distance) {
                    possibilities[index] = (ms + 1) - (2 * i)
                    break
                }
            }
        }
        return possibilities
    }

    fun part1(): Long {
        val raceMap = getLineValues(0).zip(getLineValues(1)).toMap()
        return raceMap.getPossibilities().reduce(Long::times)
    }

    fun part2(): Long {
        val raceMap = mapOf(getLineValues(0).joinValues() to getLineValues(1).joinValues())
        return raceMap.getPossibilities().first()
    }

    println(part1())
    println(part2())
}