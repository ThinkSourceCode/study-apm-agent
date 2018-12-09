package com.rao.study.apm.agent.pre_main.bytebuddy;

import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * 这个拦截器,就是字节码代理了ClassFileTransformer的方法后，交由这个拦截器去处理增强目标方法
 */
public class TimeInterceptor {

    @RuntimeType
    public static Object intercept(@Origin Method method,
                                   @SuperCall Callable<?> callable) throws Exception {
        long start = System.currentTimeMillis();
        try {
            // 原有函数执行
            return callable.call();
        } finally {
            System.out.println(method + ": took " + (System.currentTimeMillis() - start) + "ms");
        }
    }
}
