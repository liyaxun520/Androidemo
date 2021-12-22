package com.decard.iterator_demo.iterator

interface Iterator<T> {
    fun hasNext():Boolean

    fun next(): T?
}