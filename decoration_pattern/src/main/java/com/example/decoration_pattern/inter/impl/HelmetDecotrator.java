package com.example.decoration_pattern.inter.impl;

import com.example.decoration_pattern.inter.LeadDecorater;
import com.example.decoration_pattern.inter.LeapEquipment;

public class HelmetDecotrator implements LeadDecorater {
    private LeapEquipment leapEquipment;

    public HelmetDecotrator(LeapEquipment leapEquipment) {
        this.leapEquipment = leapEquipment;
    }

    @Override
    public String pack() {
        return leapEquipment.pack()+"+头盔";
    }
}
