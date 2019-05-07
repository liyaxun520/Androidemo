package com.example.observer_pattern.inter.impl;

import com.example.observer_pattern.inter.Observer;
import com.example.observer_pattern.inter.Subject;

import java.util.ArrayList;

/**
 * 被观察者
 */
public class WeatherData implements Subject {

    private final ArrayList<Object> observers;
    private float temp;
    private float humity;
    private float pressure;

    public WeatherData() {
        observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        int i = observers.indexOf(observer);
        if (i>=0) {
            observers.remove(i);
        }
    }

    @Override
    public void notifyObservers() {
        for (Object observer : observers) {
            if (observer instanceof com.example.observer_pattern.inter.Observer) {
                com.example.observer_pattern.inter.Observer os = (com.example.observer_pattern.inter.Observer) observer;
                os.update(temp,humity,pressure);
            }
        }
    }

    public void setMeasurements(float temp,float humity,float pressure) {
        this.temp = temp;
        this.humity = humity;
        this.pressure = pressure;
        notifyObservers();
    }

}
