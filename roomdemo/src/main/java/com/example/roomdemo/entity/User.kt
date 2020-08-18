package com.example.roomdemo.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.example.roomdemo.converts.Converters

@Entity(tableName = "User"/*, primaryKeys = ["id"]*/)
@TypeConverters(Converters::class)
data class User constructor(@PrimaryKey(autoGenerate = true) var id: Int = 1) {

    constructor() : this(0)

    var uuid:String ?= null
    var name:String ?= null
    var sex:String ?= null
    override fun toString(): String {
        return "User(id=$id, uuid=$uuid, name=$name, sex=$sex)"
    }


}

