package com.example.strategy_pattern.inter.impl;

import com.example.strategy_pattern.inter.FlyBehavior;

public class FlyNoWay implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("i can't fly");
    }
}
