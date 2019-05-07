package com.example.strategy_pattern.inter.impl;

import com.example.strategy_pattern.inter.FlyBehavior;

public class FlyRocketPoweded implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("火箭助力系统");
    }
}
