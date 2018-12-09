package com.rao.study.apm.agent.pre_main.bytebuddy;

import com.rao.study.apm.agent.pre_main.bytebuddy.demo.TimeDemo;

/**
 * 目标程序的main方法
 */
public class TimeMain {
    public static void main(String[] args)throws Exception{//执行时指定-javaagent:D:\idea-space\study-apm-agent\target\study-apm-agent-1.0-SNAPSHOT.jar
        TimeDemo timeDemo = new TimeDemo();
        timeDemo.fun1();
        timeDemo.fun2();
    }
}
