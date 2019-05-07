package com.example.strategy_pattern.inter.impl;

import com.example.strategy_pattern.inter.FlyBehavior;

public class FlyWithWings implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("i am flying");
    }
}
