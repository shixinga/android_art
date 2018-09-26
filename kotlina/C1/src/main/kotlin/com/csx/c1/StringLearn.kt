package com.csx.c1

/**
 * Created by shixing on 2018/9/1.
 */
fun main(args: Array<String>) {
    val s1:String = "hi"
    val s2:String = String(charArrayOf('h', 'i'))
    println(s1 == s2) //等价于equals()，就是比较引用的内容
    println(s1 === s2) // 就是比较引用

    val a:Int = 1
    val b:Int = 2
    println("" + a + " + " + b + " = " + (a + b))
    println("$a + $b = ${a + b}")
    println("hi \"csx\"")

    val str = """
    \t
    \n
    \\\\\$a
    """
    println(str)
    println(str.length)
}