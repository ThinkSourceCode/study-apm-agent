package com.rao.study.apm.agent.pre_main.invokeTransform;

import java.lang.instrument.Instrumentation;

public class TransformPremain {

    public static void premain(String agentOps, Instrumentation inst) {
        inst.addTransformer(new MyClassFileTransformImpl());
    }

}
