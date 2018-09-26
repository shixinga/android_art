package com.csx.c3

/**
 * Created by shixing on 2018/9/8.
 */
class Player {
    enum class State {
        A,
        B,
        C
    }
    var sta = State.A
    fun pause() {
        when(sta) {
            State.A -> sayA()
            else -> {
                say()
            }
        }

    }
    fun say() {
        println("haahha $sta")
    }
    fun sayA() {
        println("haahha aaaa")
    }
}

fun main(args: Array<String>) {
//    var p = Player()
//    p.pause()
    val x = 6
    when(x) {
        !is Int -> println("is int ")
        in 1..100 -> println("in 1 ---- 100")
        !in -12..3 -> println("not in -12..3")
    }
}