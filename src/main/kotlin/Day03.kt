import java.io.File

fun main() {
    val path = "src/main/kotlin/inputs/Day03.txt"
    val file = File(path).absoluteFile.readLines()
    val specialChars = listOf('#', '*', '@', '+', '-', '%', '&', '/', '$', '=')
    val multiply = '*'

    fun getNumber(file: String, index: Int): Int {
        val prefix = file.substring(0, index).takeLastWhile { it.isDigit() }
        val suffix = file.substring(index).takeWhile { it.isDigit() }

        return "$prefix$suffix".toInt()
    }

    fun part1(): Int {
        var point = 0

        file.forEachIndexed { index, line ->
            line.forEachIndexed { ix, ch ->
                if (ch in specialChars) {
                    for (i in -1..1) {
                        for (k in listOf(0, 1, -1)) {
                            if (file[index + i][ix + k].isDigit()) {
                                point += getNumber(file[index + i], ix + k)
                                if (k == 0) break
                            }
                        }
                    }
                }
            }
        }

        return point
    }

    fun part2(): Int {
        var point = 0

        file.forEachIndexed { index, line ->
            line.forEachIndexed { ix, ch ->
                if (ch == multiply) {
                    var count = 0
                    val nums = mutableListOf<Int>()

                    for (i in -1..1) {
                        for (k in listOf(0, 1, -1)) {
                            if (file[index + i][ix + k].isDigit()) {
                                nums.add(getNumber(file[index + i], ix + k))
                                count += 1
                                if (k == 0) break
                            }
                        }
                        if (count > 2) break
                    }

                    if (count == 2) point += nums.reduce(Int::times)
                }
            }
        }

        return point
    }


    println(part1())
    println(part2())

}


