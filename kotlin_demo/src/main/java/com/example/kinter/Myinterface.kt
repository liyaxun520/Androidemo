package com.example.kinter

interface Myinterface {
    var name: String   //接口中属性不能赋值，实现类实现后重新赋值
    fun eat(){
        println("===================eat=============")
    }

    fun drink()  //接口类中方法为抽象方法
}