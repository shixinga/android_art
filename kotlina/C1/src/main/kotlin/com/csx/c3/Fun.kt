package com.csx.c3

/**
 * Created by shixing on 2018/9/2.
 */

fun main(args: Array<String>) {
//    println("hi ${args[0]}")
    val a : Int = 1
    val b : Int = 2
    println("sum1= ${sum1(a,b)}")
    println("sum2= ${sum2(a,b)}")
    println("匿名函数sum3= ${sum3(a,b)}")
    println("lambda(匿名函数)sum4= ${sum4(a,b)}")

}

fun sum1(a:Int, b : Int) : Int {
    return a + b
}
fun sum2(a:Int, b : Int) = a + b

val sum3 = fun(a:Int, b:Int) = a + b

var sum4 = {a:Int, b : Int ->
    println("a + b aaaa")
    a + b
}