package com.drizzle.java8.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 原子性测试
 */
public class AtomicTest {
    static class MyTest {
        // 如此处是int，则线程不安全
        // 如此处是int，加 synchronized 则线程安全
        public volatile AtomicInteger number = new AtomicInteger();

        public void incr() {
            number.getAndIncrement();
        }
    }

    public static void main(String[] args) {
        MyTest myTest = new MyTest();
        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                for (int j = 1; j <= 1000; j++) {
                    myTest.incr();
                }
            }, "Thread" + String.valueOf(i)).start();
        }

        //等线程执行结束了，输出number值
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println("当前number：" + myTest.number);
    }
}