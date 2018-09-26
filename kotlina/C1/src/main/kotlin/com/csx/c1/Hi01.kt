package com.csx.c1

/**
 * Created by shixing on 2018/8/31.
 */
val maxInt : Int = Integer.MAX_VALUE
val minInt : Int = Integer.MIN_VALUE
val minLong : Long = Long.MIN_VALUE
val maxLong : Long = Long.MAX_VALUE
val aBoolean : Boolean = true
var minFloat : Float = Float.MIN_VALUE
var maxFloat : Float = Float.MAX_VALUE
fun main(args: Array<String>) {
    println("hi world !")
    println(maxInt)
    println(Math.pow(2.0, 31.0) - 1)
    println(minInt)
    println(-Math.pow(2.0, 31.0))
    println(minLong)
    println(-Math.pow(2.0, 63.0))
    println(maxLong)
    println(Math.pow(2.0, 63.0) - 1)
    println(aBoolean)
    println(minFloat)
    println(maxFloat)
}