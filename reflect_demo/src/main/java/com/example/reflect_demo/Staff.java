package com.example.reflect_demo;

import android.util.Log;

public class Staff {
    public static final String TAG = Staff.class.getSimpleName();
    private String name;
    private String depart;
    private int age;

    public Staff() {
    }

    public Staff(String name, String depart, int age) {
        this.name = name;
        this.depart = depart;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void work(){
        Log.e(TAG,depart+"中的"+age+"岁的"+name+"正在工作");
    }
    @Override
    public String toString() {
        return "Staff{" +
                "name='" + name + '\'' +
                ", depart='" + depart + '\'' +
                ", age=" + age +
                '}';
    }
}
