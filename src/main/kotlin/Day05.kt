@file:Suppress("UNCHECKED_CAST")

import java.io.File

fun main() {
    val path = "src/main/kotlin/inputs/Day05.txt"
    val file = File(path).absoluteFile

    val seeds = file.readLines().first().substringAfter(':').trim().split(' ').map { it.toLong() }
    val mapList = listOf(3, 32, 34, 53, 55, 97, 99, 117, 119, 132, 134, 144, 146, 190).chunked(2)
        .map { file.readLines().getMap(it.first(), it.last()) }

    fun MutableList<Pair<Long, Long>>.getMinimumLocation(): Long =
        mapList.fold(this) { old, map ->
            buildList {
                while (old.size > 0) {
                    val (first, last) = old.removeLast()
                    val size = this.size
                    map.forEach {
                        val start = maxOf(first, it.second)
                        val end = minOf(last, it.second + it.third)
                        if (start < end) {
                            add(start - it.second + it.first to end - it.second + it.first)
                            if (start > first) old.add(first to start)
                            if (end < last) old.add(end to last)
                            return@forEach
                        }
                    }
                    if (size == this.size) add(first to last)
                }
            }.toMutableList()
        }.minOf { it.first }


    fun part1(): Long = seeds.map { it to it + 1 }.toMutableList().getMinimumLocation()

    fun part2(): Long = seeds.chunked(2)
        .map { it.first() to it.first() + it.last() - 1 }
        .toMutableList()
        .getMinimumLocation()

    println(part1())
    println(part2())

}

fun List<String>.getMap(from: Int, to: Int): List<Triple<Long, Long, Long>> {
    return this.subList(from, to).map {
        it.split(' ')
            .apply {
                return@map Triple(first().toLong(), get(1).toLong(), last().toLong())
            }
    } as List<Triple<Long, Long, Long>>
}

