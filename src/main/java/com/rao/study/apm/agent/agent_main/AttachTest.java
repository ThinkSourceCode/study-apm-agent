package com.rao.study.apm.agent.agent_main;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.util.List;
import java.util.Properties;

public class AttachTest extends Thread {

    private final List<VirtualMachineDescriptor> listBefore;
    private final String jar;

    AttachTest(String attachJar, List<VirtualMachineDescriptor> vms) {
        listBefore = vms;  // 记录程序启动时的 VM 集合
        jar = attachJar;
    }

    @Override
    public void run() {
        VirtualMachine vm = null;
        List<VirtualMachineDescriptor> listAfter = null;
        try {
            int count = 0;
            while (true) {
                listAfter = VirtualMachine.list();
                for (VirtualMachineDescriptor vmd : listAfter) {
                    if (!listBefore.contains(vmd)) {
                        // 如果 VM 有增加，我们就认为是被监控的 VM 启动了
                        // 这时，我们开始监控这个 VM
                        vm = VirtualMachine.attach(vmd);
                        break;
                    }
                }
                Thread.sleep(500);
                count++;
                if (null != vm || count >= 10) {
                    break;
                }
            }
            vm.loadAgent(jar);
//            vm.detach();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        test();
    }

    public static void test() throws Exception {
        for (VirtualMachineDescriptor vmd : VirtualMachine.list()) {
            System.out.println(vmd);
            if (vmd.displayName().contains("TestMainInJar")) {//找到指定的目标虚拟机
                //通过provider或者进程id，连接上指定的虚拟机,并返回对应的子VirtualMachine实例
                VirtualMachine vm = VirtualMachine.attach(vmd);
                //获取目标虚拟机的属性
                Properties properties = vm.getAgentProperties();
                Properties sysProperties = vm.getSystemProperties();
                //载入java的agent,并调用agent的agentmain方法
                vm.loadAgent("C:\\Users\\honey.rao\\Desktop\\study-apm-agent-1.0-SNAPSHOT.jar");
                System.out.println("loaded");
                //从虚拟机中断开
                vm.detach();
                System.out.println("detached");
                break;
            }
        }
    }

    public static void testByPid() throws Exception {
        VirtualMachine attach = VirtualMachine.attach("12944");
        System.out.println(attach.id());
        //获取目标虚拟机的属性
        Properties properties = attach.getAgentProperties();
        Properties sysProperties = attach.getSystemProperties();
        //载入java的agent,并调用agent的agentmain方法
        attach.loadAgent("C:\\Users\\honey.rao\\Desktop\\study-apm-agent-1.0-SNAPSHOT.jar");
        System.out.println("loaded");
        //从虚拟机中断开
        attach.detach();
        System.out.println("detached");
    }

    public static void testByThread() throws Exception {
        new AttachTest("C:\\Users\\honey.rao\\Desktop\\study-apm-agent-1.0-SNAPSHOT.jar", VirtualMachine.list()).run();
    }
}
