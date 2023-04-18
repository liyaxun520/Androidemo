package com.decard.hiltdagger_demo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.decard.hiltdagger_demo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    var activityMainBinding: ActivityMainBinding? = null

    @Inject
    lateinit var car: Car

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) //        activityMainBinding =
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        car.testDriver()

    }
}