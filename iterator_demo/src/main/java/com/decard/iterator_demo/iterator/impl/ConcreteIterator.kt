package com.decard.iterator_demo.iterator.impl

import com.decard.iterator_demo.iterator.Iterator

class ConcreteIterator<T> : Iterator<T> {

    private var list: List<T> = arrayListOf()
    private var cursor = 0

    constructor(list: List<T>) {
        this.list = list
    }


    override fun hasNext(): Boolean {
        return cursor < list.size
    }

    override fun next(): T? {
        var obj: T? = null
        if (this.hasNext()) {
            obj = list.get(cursor++)
        }
        return obj
    }
}