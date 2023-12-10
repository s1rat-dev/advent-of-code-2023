import java.io.File

fun main() {
    val path = "src/main/kotlin/inputs/Day09.txt"
    val file = File(path).absoluteFile

    val histories = buildList {
        file.forEachLine {
            add(it.split(" ").map { it.toInt() })
        }
    }

    fun getHistoriesSummary(body: (list: MutableList<List<Int>>) -> List<Int>): List<List<Int>> {
        return buildList {
            histories.forEach {
                val newList = mutableListOf<List<Int>>()
                var temp = it
                while (!temp.all { it == 0 }) {
                    newList.add(temp)
                    temp = buildList {
                        for (i in temp.indices) {
                            if (i == temp.size - 1) continue
                            add(temp[i + 1] - temp[i])
                        }
                    }
                }
                add(body.invoke(newList))
            }
        }
    }

    fun part1(): Int = getHistoriesSummary { list -> list.map { it.last() } }.flatten().sum()

    fun part2(): Int = getHistoriesSummary { list -> list.map { it.first() } }.sumOf {
        it.reversed().fold(0) { old, value -> value - old } as Int
    }

    println(part1())
    println(part2())
}