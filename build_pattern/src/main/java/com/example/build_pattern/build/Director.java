package com.example.build_pattern.build;

public class Director {
    private Builder mBuild = null;

    public Director(Builder build) {
        this.mBuild = build;
    }

    //指挥装机人员组装电脑
    public void Construct(String cpu, String memory, String hd) {
        mBuild.buildCPU(cpu);
        mBuild.buildMemory(memory);
        mBuild.buildHD(hd);
    }
}
