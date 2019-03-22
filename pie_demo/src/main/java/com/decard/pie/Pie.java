package com.decard.pie;


/**
 * A module to init PieChart
 */
public class Pie {
    public int pieColor;
    public float pieValue;
    public String pieString;

    public Pie(float pieValue,String pieString,int pieColor){
        this.pieValue = pieValue;
        this.pieColor = pieColor;
        this.pieString = pieString;
    }
}
