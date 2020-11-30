package com.example.kinter

class ConcreteChild(override var name: String) :Myinterface{
    override fun drink() {
        println("=====${name}在喝五粮液==========")
    }

    override fun eat() {
        super.eat()
        println("=======${name}在吃火龙果========")
    }

}