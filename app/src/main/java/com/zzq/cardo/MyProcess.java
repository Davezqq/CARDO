package com.zzq.cardo;

/**
 * Created by apple on 2018/4/24.
 */

public class MyProcess {
    private String name;
    private  String memory;

    public MyProcess(String name, String memory) {
        this.name = name;
        this.memory = memory;
    }

    public String getName() {
        return name;
    }

    public String getMemory() {
        return memory;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }
}
