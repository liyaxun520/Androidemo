package com.example.build_pattern.build;

public class ConcreteBuilder extends Builder {
    //创建产品实例
    private Computer mComputer = new Computer();

    @Override
    public void buildCPU(String cpu) {//组装CPU
        mComputer.setCPU(cpu);
    }

    @Override
    public void buildMemory(String memory) {//组装内存
        mComputer.setMemory(memory);
    }

    @Override
    public void buildHD(String hd) {//组装硬盘
        mComputer.setHD(hd);
    }

    @Override
    public Computer create() {//返回组装好的电脑
        return mComputer;
    }


}
