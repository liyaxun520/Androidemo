package com.example.strategy_pattern.inter.impl;

import com.example.strategy_pattern.inter.QuackBehavior;

public class Quack implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("Quack");
    }
}
