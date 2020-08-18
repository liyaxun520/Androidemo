package com.example.p1

open class Greeter constructor(val obj:Any){
    fun greet(){
        if (obj is String) {
            println("Hello,input is $obj")
        }
        if(obj is Greeter){
            var greeter :Greeter = obj
            greeter.greet()
        }
    }
}


fun main(args: Array<String>) {
    Greeter("lison").greet()
    Greeter(Greeter("zs")).greet()
    test()

}

fun test(){
    println("测试方法调用")
    A().sendClass()
    var add = A().add(10, 30)
    println("加法  $add")
    var sencondAdd = A().sencondAdd(20, 30)
    println("推断计算加法  $sencondAdd")

    A().vars(5,4,3,2,1)   //存储多个参数)
}

class A{
    init { // 当该类调用时执行
        println("=====================================")
    }
    fun sendClass(){
        println("kotlin文件中的第二类内部方法")
    }

    fun  add(a:Int,b:Int):Int{
        return a+b
    }
    fun sencondAdd(a:Int,b:Int) = a+b

    fun vars(vararg a:Int){
        for(i in a){
            println("参数遍历  $i")
        }
    }
}