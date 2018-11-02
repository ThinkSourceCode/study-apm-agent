package com.rao.study.apm.agent.agent_main;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定义自己的转换类
 */
public class MyTransformer implements ClassFileTransformer {

    final static String prefix = "\nlong startTime = System.currentTimeMillis();\n";
    final static String postfix = "\nlong endTime = System.currentTimeMillis();\n";

    // 被处理的方法列表
    final static Map<String, List<String>> methodMap = new HashMap<String, List<String>>();

    public MyTransformer() {
        //预先指定哪些类需要转换
        add("com.rao.study.apm.agent.agent_main.TimeTest.sayHello");
        add("com.rao.study.apm.agent.agent_main.TimeTest.sayHello2");
    }

    private void add(String methodString) {
        String className = methodString.substring(0, methodString.lastIndexOf("."));
        String methodName = methodString.substring(methodString.lastIndexOf(".") + 1);
        List<String> list = methodMap.get(className);
        if (list == null) {
            list = new ArrayList<String>();
            methodMap.put(className, list);
        }
        list.add(methodName);
    }


    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        //将类路径改为类全名称
        className = className.replace("/", ".");
        // 判断加载的class的包路径是不是需要监控的类
        //addTransformer 方法并没有指明要转换哪个类。转换发生在 premain 函数执行之后，main 函数执行之前，这时每装载一个类，transform 方法就会执行一次，看看是否需要转换，所以，在 transform（Transformer 类中）方法中，程序用 className.equals("TransClass") 来判断当前的类是否需要转换。
        if (methodMap.containsKey(className)) {
            CtClass ctclass = null;
            try {
                // 使用全称,用于取得字节码类<使用javassist>
                ctclass = ClassPool.getDefault().get(className);
                for (String methodName : methodMap.get(className)) {
                    String outputStr = "\nSystem.out.println(\"this method " + methodName + " cost:\" +(endTime - startTime) +\"ms.\");";

                    // 得到这方法实例
                    CtMethod ctmethod = ctclass.getDeclaredMethod(methodName);
                    // 新定义一个方法叫做比如sayHello$old
                    String newMethodName = methodName + "$old";
                    // 将原来的方法名字修改
                    ctmethod.setName(newMethodName);

                    // 创建新的方法，复制原来的方法，名字为原来的名字
                    CtMethod newMethod = CtNewMethod.copy(ctmethod, methodName, ctclass, null);

                    // 构建新的方法体
                    StringBuilder bodyStr = new StringBuilder();
                    bodyStr.append("{");
                    bodyStr.append(prefix);
                    // 调用原有代码，类似于method();($$)表示所有的参数
                    bodyStr.append(newMethodName + "($$);\n");
                    bodyStr.append(postfix);
                    bodyStr.append(outputStr);
                    bodyStr.append("}");

                    // 替换新方法
                    newMethod.setBody(bodyStr.toString());
                    // 增加新方法
                    ctclass.addMethod(newMethod);
                }
                //返回字节码
                return ctclass.toBytecode();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        return null;
    }
}
