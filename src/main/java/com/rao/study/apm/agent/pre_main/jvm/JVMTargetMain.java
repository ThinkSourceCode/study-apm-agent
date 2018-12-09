package com.rao.study.apm.agent.pre_main.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * 目标程序
 */
public class JVMTargetMain {
    public static void main(String[] args) throws Exception {//执行时指定-javaagent:D:\idea-space\study-apm-agent\target\study-apm-agent-1.0-SNAPSHOT.jar
        boolean is = true;
        while (is) {
            List<Object> list = new ArrayList<Object>();
            list.add("hello world");
        }
    }
}
