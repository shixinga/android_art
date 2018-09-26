package com.csx.c1

/**
 * Created by shixing on 2018/9/1.
 */
/*class Shuaige(var name:String, var age:Int, var sex: String) {
    init {
        println("name=$name, age=$age, sex=${sex}")
    }
}*/

open class Person(var name:String, var age:Int, var sex: String) {
    init {
        println("name=$name, age=$age, sex=${sex}")
    }
}
class Shuaige(name:String, age:Int, sex: String): Person(name, age, sex) {

}

fun main(args: Array<String>) {
    var person: Person = Shuaige("csx", 231, "1男")
}