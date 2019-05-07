package com.example.observer_pattern

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.observer_pattern.inter.impl.CurrentConditionDislay
import com.example.observer_pattern.inter.impl.WeatherData

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        testObserver()
    }

    private fun testObserver() {
        var data = WeatherData()
        var conditionDislay = CurrentConditionDislay(data)

        for(i in 1..10){
            data.setMeasurements(10f * i,20f* i,30f* i)
        }
    }
}
