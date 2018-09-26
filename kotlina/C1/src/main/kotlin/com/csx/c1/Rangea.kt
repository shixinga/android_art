package com.csx.c1

/**
 * Created by shixing on 2018/9/1.
 */
val range:IntRange = 0..1024
val rangeExclusive = 0 until 1024

fun main(args: Array<String>) {
    println(range.isEmpty())
    println(rangeExclusive.contains(0))
    println(rangeExclusive.contains(1024))
    println(100 in range)

    for (i in rangeExclusive) {
        print("$i,")
    }
}