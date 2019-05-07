package com.example.observer_pattern.inter.impl;

import android.util.Log;

import com.example.observer_pattern.inter.DisplayElement;
import com.example.observer_pattern.inter.Observer;
import com.example.observer_pattern.inter.Subject;

public class CurrentConditionDislay implements Observer, DisplayElement {
    public static final String TAG = CurrentConditionDislay.class.getSimpleName();
    private float temp;
    private float humity;
    private float pressure;
    private Subject weatherData;

    public CurrentConditionDislay(Subject weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    @Override
    public void display() {
        Log.d(TAG,"当前温度  "+temp+"   湿度 "+humity+"  压强 "+pressure);
    }

    @Override
    public void update(float temp, float humidity, float pressure) {

        this.temp = temp;
        this.humity = humidity;
        this.pressure = pressure;

        display();

    }
}
