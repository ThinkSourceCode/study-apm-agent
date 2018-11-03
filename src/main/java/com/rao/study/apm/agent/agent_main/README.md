为了便于测试，可以将目标代码和代理代码放在一块进行测试
> 
    比如：ob目录下的就是目标的代码，此时编译时，目标代码和代理代码在同一个jar包下,运行TestMainInJar其他业务代码
    再运行AttachTest指定agentain的代理jar，所以此时会断点到当前jar,这样便于调试

>
    如果要将目标的代码和代理的代码分离打包成多个jar，单独运行目标代码，则还是运行AttachTest
    
>
    同样的，你也可以通过instrumentation的inst.addTransformer()方法加入ClassFileTransformer的实现类，然后对指定的class进行修改。
    
    但是在运行时对class修改和premain的方式对class的修改是有点差别的，至少在jdk6以及以前的版本是这样的。
    
    通过premain的方式在应用启动前对class可以做任何修改，可以添加class,删除class,添加删除修改方法体，添加成员变量等等没有任何限制，
    
    然而通过agentmain的方式在运行时修改class是有限制的,，比如在class已经被加载过的情况下，是不能对class添加，删除方法的，只能重定义方法体，这对很多功能其实做了限制。相信jdk6以后的版本应该会放开这个限制吧。