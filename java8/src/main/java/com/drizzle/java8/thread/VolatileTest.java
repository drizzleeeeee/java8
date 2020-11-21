package com.drizzle.java8.thread;

import java.util.concurrent.TimeUnit;

/**
 * 可见性测试
 */
public class VolatileTest {

    static class MyTest {

        // 如此处去除volatile，则main方法里的while循环会一直进行，因为参数变动不可见
        public volatile int number = 0;

        public void changeNumber() {
            number = 100;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyTest myTest = new MyTest();

        new Thread(() -> {
            System.out.println(String.format("线程%s开始执行", Thread.currentThread().getName()));

            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            myTest.changeNumber();
            System.out.println(String.format("线程%s的number：%d", Thread.currentThread().getName(), myTest.number));
        }, "NewThread").start();

        while (myTest.number == 0) {

        }

        System.out.println("执行完毕");
    }
}