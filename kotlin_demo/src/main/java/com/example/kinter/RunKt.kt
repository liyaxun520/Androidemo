package com.example.kinter

fun main(args:Array<String>) {
    var concreteChild = ConcreteChild("lison") as Myinterface
    concreteChild.eat()
    concreteChild.drink()

    var concreteChild1 = ConcreteChild("carery") as Myinterface
    concreteChild1.eat()
    concreteChild1.drink()
}