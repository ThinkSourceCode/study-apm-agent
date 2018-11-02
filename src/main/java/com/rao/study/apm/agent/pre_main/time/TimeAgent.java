package com.rao.study.apm.agent.pre_main.time;

import java.lang.instrument.Instrumentation;

public class TimeAgent {
    public static void premain(String agentOps, Instrumentation inst) {
        System.out.println("=========premain方法执行========");
        System.out.println(agentOps);
        // 添加Transformer
        inst.addTransformer(new MyTransformer());
    }
}
