package com.csx.c3

/**
 * Created by shixing on 2018/9/2.
 */
class X

class A {
    var b:Int = 1
    lateinit var s:String
    //var 类型的变量可以用lateinit来延迟初始化， 而val类型的变量用lazy来延迟初始化
    lateinit var x1 :X
    val x2 : X by lazy {
        println("init x aaaaaaaaaaaaa")
        X()
    }
}

fun main(args: Array<String>) {
    var a = A()
    println(a.b)
    println(a.x2)
    a.s = "dajdiojwijd"
    println(a.s)

}