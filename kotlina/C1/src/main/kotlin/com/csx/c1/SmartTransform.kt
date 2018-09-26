package com.csx.c1

/**
 * Created by shixing on 2018/9/1.
 */


fun main(args: Array<String>) {
//    var parent:Parent = Child()
//    if (parent is Child) {
//        println(parent.childMethoda())
//    }

    var parent: Parent = Parent()
    var child: Child?  = parent as? Child  //第一个?表示变量child可以为null，第2个?表示如果强转失败的话就返回为null
    println(child)
}