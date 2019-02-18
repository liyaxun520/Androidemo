package com.example.androidemo.jni;

import com.example.androidemo.Person;

public class MyJni {

    public native static  String getStrFromC();


    public native static double getRectArea(double width,double height);

    public native static Person getPerson();
}
