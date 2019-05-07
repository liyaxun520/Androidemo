package com.example.ndkdemo.jni;

import com.example.ndkdemo.Person;

public class MyJni {

    public native static  String getStrFromC();


    public native static double getRectArea(double width,double height);

    public native static Person getPerson();

}
