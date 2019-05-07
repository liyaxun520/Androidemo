package com.example.decoration_pattern.inter.impl;

import com.example.decoration_pattern.inter.LeadDecorater;
import com.example.decoration_pattern.inter.LeapEquipment;

public class ArmsDecorator implements LeadDecorater {

    private LeapEquipment leapEquipment;

    public ArmsDecorator(LeapEquipment leapEquipment) {
        this.leapEquipment = leapEquipment;
    }

    @Override
    public String pack() {
        return leapEquipment.pack()+"武器";
    }
}
