package com.example.decoration_pattern.inter.impl;

import com.example.decoration_pattern.inter.LeadDecorater;
import com.example.decoration_pattern.inter.LeapEquipment;

public class ClothesDecorator implements LeadDecorater {
    private LeapEquipment leapEquipment;

    public ClothesDecorator(LeapEquipment leapEquipment) {
        this.leapEquipment = leapEquipment;
    }

    @Override
    public String pack() {
        return leapEquipment.pack()+"+上衣";
    }
}
