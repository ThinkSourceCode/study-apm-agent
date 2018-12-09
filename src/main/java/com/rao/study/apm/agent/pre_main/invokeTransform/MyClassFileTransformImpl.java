package com.rao.study.apm.agent.pre_main.invokeTransform;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class MyClassFileTransformImpl implements ClassFileTransformer {
    //启动目标程序,就会通过premain添加transformer,并在ClassFile加载触发ClassFileLoad事件（代码在jdk的JPLISAgent.c和InvocationAdapter.c中可以看到）并调用transform方法,从而实现在jvm启动时就对目标类进行增强拦截代理处理
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

        System.out.println("sdfsdf");

        return new byte[0];
    }
}
