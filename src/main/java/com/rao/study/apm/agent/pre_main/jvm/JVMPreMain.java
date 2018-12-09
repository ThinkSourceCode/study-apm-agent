package com.rao.study.apm.agent.pre_main.jvm;

import java.lang.instrument.Instrumentation;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class JVMPreMain {
    public static void premain(String agentOps, Instrumentation inst){
        System.out.println("this is an perform monitor agent.");

        //定期去获取jvm信息
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            public void run() {
                Metric.printMemoryInfo();
                Metric.printGCInfo();
            }
        }, 0, 5000, TimeUnit.MILLISECONDS);

    }
}
