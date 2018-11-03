package com.rao.study.apm.agent.agent_main;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class AgentMain {
    /**
     * agentmain是在目标程序main方法执行之后才执行的方法
     * @param agentArgs
     */
    public static void agentmain(String agentArgs, Instrumentation instrumentation) throws UnmodifiableClassException {
        System.out.println("MyAgentMain agentmain attach...");

        for (Class clazz :instrumentation.getAllLoadedClasses()){
            System.out.println(clazz.getName());
        }
//        inst.addTransformer(newMonitorAgentTransformer(),true);
//       inst.retransformClasses(WaitTest.class);

        System.out.println("MyAgentMain agentmain end...");
    }

}
