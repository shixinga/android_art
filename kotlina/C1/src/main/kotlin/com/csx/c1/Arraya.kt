package com.csx.c1

/**
 * Created by shixing on 2018/9/1.
 */
val HELLOW_WORLD : String = "hello world"
val helloWorld :String = HELLOW_WORLD
var nullableHelloWorld : String = helloWorld
val helloWroldArr : Array<Char> = arrayOf('h','e','l','l','o')
val helloWorldCharArr: CharArray = charArrayOf('h','i')
val hiLength : Int = helloWorldCharArr.size

fun main(args: Array<String>) {
    println("HELLOW_WORLD= $HELLOW_WORLD")
    println("helloWorld= $helloWorld")
    println("nullableHelloWorld= $nullableHelloWorld")
    println("helloWroldArr= " + helloWroldArr)
    println("helloWroldArr= " + helloWroldArr.joinToString("_"))
    println("helloWorldCharArr= " + String(helloWorldCharArr))
    println("hiLength= $hiLength")
    println("class name=" + nullableHelloWorld::class.java.simpleName)
    println("class path name=" + nullableHelloWorld::class.java.name)


}