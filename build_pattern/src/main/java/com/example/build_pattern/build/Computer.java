package com.example.build_pattern.build;

public class Computer {
    private String mCPU;
    private String mMemory;
    private String mHD;

    public void setCPU(String CPU) {
        mCPU = CPU;
    }

    public void setMemory(String memory) {
        mMemory = memory;
    }

    public void setHD(String HD) {
        mHD = HD;
    }

    @Override
    public String toString() {
        return "Computer{" +
                "mCPU='" + mCPU + '\'' +
                ", mMemory='" + mMemory + '\'' +
                ", mHD='" + mHD + '\'' +
                '}';
    }
}
