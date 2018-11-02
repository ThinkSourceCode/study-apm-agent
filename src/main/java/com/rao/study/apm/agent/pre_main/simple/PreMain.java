package com.rao.study.apm.agent.pre_main.simple;

import java.lang.instrument.Instrumentation;

public class PreMain {
    //在目标程序的main方法执行之前调用,通过-jaavaagent:执行
    public static void premain(String agentOps){
        System.out.println("在main之前执行premain,agentOps="+agentOps);
    }

    public static void premain(String agentOps, Instrumentation inst){
        System.out.println("在main之前执行premain,agentOps="+agentOps);
    }
}
