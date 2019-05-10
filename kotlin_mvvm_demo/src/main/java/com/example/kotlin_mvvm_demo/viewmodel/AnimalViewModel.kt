package com.example.kotlin_mvvm_demo.viewmodel

import android.databinding.ObservableField
import com.example.kotlin_mvvm_demo.model.Animal

class AnimalViewModel(val animal:Animal){
    val animalInfo = ObservableField<String>("")

    //点击按钮调用方法
    fun shout(){
        animal.shoutCount ++
        animalInfo.set("${animal.name}叫了${animal.shoutCount}声")
    }
}