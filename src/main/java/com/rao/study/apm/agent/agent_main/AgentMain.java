package com.rao.study.apm.agent.agent_main;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class AgentMain {
    /**
     * agentmain是在目标程序main方法执行之后才执行的方法
     * @param agentArgs
     */
    public static void agentmain(String agentArgs, Instrumentation instrumentation) throws UnmodifiableClassException {
        System.out.println("在main方法执行后执行,agentargs="+agentArgs);
        System.out.println("agent main Done");
        instrumentation.addTransformer(new MyTransformer(),true);
        instrumentation.retransformClasses(TimeTest.class);
    }
}
