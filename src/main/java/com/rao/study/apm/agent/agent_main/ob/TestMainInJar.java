package com.rao.study.apm.agent.agent_main.ob;

public class TestMainInJar {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(new TransClass().getNumber());
        while (true) {
            Thread.sleep(2000);
            int number = new TransClass().getNumber();
            System.out.println(number);
        }
    }

}
