package com.rao.study.apm.agent.pre_main.bytebuddy;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

/**
 * 这里使用byte-buddy中自带的AgentBudiler 实现了 我们自定义的ClassFileTransformer的实现
 */
public class TimePreMain {
    public static void premain(String agentOps, Instrumentation inst) {
        System.out.println("this is an perform monitor agent.");

        //采用byte-buddy已有的对ClassFileTransformer的实现增强
        //动态代理ClassFileTransformer 创建一个Transformer的对象
        AgentBuilder.Transformer transformer = new AgentBuilder.Transformer() {
            @Override
            public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder,
                                                    TypeDescription typeDescription,
                                                    ClassLoader classLoader) {
                return builder
                        .method(ElementMatchers.<MethodDescription>any()) // 拦截任意方法
                        .intercept(MethodDelegation.to(TimeInterceptor.class)); // 委托,指明拦截目标方法的拦截器
            }
        };

        AgentBuilder.Listener listener = new AgentBuilder.Listener() {
            @Override
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, DynamicType dynamicType) {}

            @Override
            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module) { }

            @Override
            public void onError(String typeName, ClassLoader classLoader, JavaModule module, Throwable throwable) { }

            @Override
            public void onComplete(String typeName, ClassLoader classLoader, JavaModule module) { }
        };

        //通过AgentBuilder将transformer添加到Instrumentation
        /**
         * ResettableClassFileTransformer.transform
         */
        new AgentBuilder
                .Default()
                .type(ElementMatchers.nameStartsWith("com.rao.study.apm.agent.pre_main.time.bytebuddy.demo")) // 指定需要拦截的类
                .transform(transformer)//指定转换类,new Transformer.Compound(this.transformer, transformer),net.bytebuddy.agent.builder.AgentBuilder.Transformer.Compound.transform
                .with(listener)
                .installOn(inst);//将转换类对象添加到Instrumentation中
        //ResettableClassFileTransformer classFileTransformer = makeRaw(); 创建了一个ClassFileTransformer实例
        //instrumentation.addTransformer(classFileTransformer, redefinitionStrategy.isRetransforming(instrumentation));

    }
}
