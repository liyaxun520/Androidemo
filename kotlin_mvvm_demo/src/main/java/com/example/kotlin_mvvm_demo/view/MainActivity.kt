package com.example.kotlin_mvvm_demo.view

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.kotlin_mvvm_demo.R
import com.example.kotlin_mvvm_demo.databinding.ActivityMainBinding
import com.example.kotlin_mvvm_demo.model.Animal
import com.example.kotlin_mvvm_demo.viewmodel.AnimalViewModel

class MainActivity : AppCompatActivity() {

    var binding:ActivityMainBinding ?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        var animalViewModel = AnimalViewModel(Animal("dog", 0))
        binding!!.animalVm = animalViewModel

    }
}
