package com.rao.study.apm.agent.pre_main.bytebuddy.demo;

//表示目标程序的类
public class TimeDemo {
    public void fun1() throws Exception {
        System.out.println("this is fun 1.");
        Thread.sleep(500);
    }

    public void fun2() throws Exception {
        System.out.println("this is fun 2.");
        Thread.sleep(500);
    }
}
