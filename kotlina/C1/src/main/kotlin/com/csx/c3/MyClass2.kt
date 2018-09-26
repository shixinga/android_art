package com.csx.c3

/**
 * Created by shixing on 2018/9/5.
 */
//构造函数中的变量只有被 val/var修饰的变量才是类的属性
class Person(var name:String, var age : Int, nameNotField : String) {
    fun sing(singname:String) {
        println("${name} singing " + singname)
//        println("${name} singing " + singname + " ${nameNotField}")  //编译通不过
    }

}

class User {
    var age = 12
    //当一个类只有一个成员变量时，get()和set()才会运行
//    var age2 = 33

    get() {
//        println("get age=${age}") //会造成Stack Overflow
        println("get age")
        return field
    }
    set(value) {
        println("set age=${value}")
        field = value
    }
}
fun main(args: Array<String>) {
//    var p = Person("csx", 22)
//    p.sing("don't you worry you child")

    var u = User()
    println(u.age)

    u.age = 233
    println(u.age)
}