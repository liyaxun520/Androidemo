package com.example.build_pattern.build;

/**
 * 建造者模式
 */
public abstract class Builder {
    public abstract void buildCPU(String cpu);//组装CPU

    public abstract void buildMemory(String memory);//组装内存

    public abstract void buildHD(String hd);//组装硬盘

    public abstract Computer create();//返回组装好的电脑

}
