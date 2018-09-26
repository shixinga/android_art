package com.csx.c1

/**
 * Created by shixing on 2018/9/1.
 */
fun getName():String? {
    return null
}

fun main(args: Array<String>) {
//    println(getName()?.length)

//    var name:String = getName()?:return;
//    println(name.length)

//    var sex:String = "hi world" //不可以为null
//    println(sex!!.length)

    var age : String? = null //可以为null
    println(age?.length)
}