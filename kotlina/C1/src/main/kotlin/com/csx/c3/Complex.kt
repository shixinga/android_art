package com.csx.c3

/**
 * Created by shixing on 2018/9/6.
 */

class Complexa(var real:Double, var imagenary : Double) {
    //+ 被重载了
    operator fun plus(other : Complexa) : Complexa {
        return Complexa(real + other.real, imagenary + other.imagenary)
    }
    //+ 被重载了
    operator fun plus(otherInt : Int) : Complexa {
        return Complexa(real + otherInt, imagenary)
    }

    operator fun invoke():Double {
        return real * 2
    }
    override fun toString(): String {
        return "${real} + ${imagenary}i"
    }
}

fun main(args: Array<String>) {
    var com = Complexa(3.0, 4.0)

    println(com + Complexa(2.0, 3.0))

    println(com + 5)

    println(com()) //就因为在Complexa类中重写了操作符()对应的invoke()


    //if表达式
    var a = 1
    var mode = if(a == 1) {
        true
    } else {
        false
    }
    println(mode)
}