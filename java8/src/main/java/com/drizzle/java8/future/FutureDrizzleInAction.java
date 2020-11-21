package com.drizzle.java8.future;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Description: Future自实现测试类
 * @Author Administrator
 * @Date 2020/11/21 14:47
 */
public class FutureDrizzleInAction {

    public static void main(String[] args) throws InterruptedException {

        // 调用
        FutureDrizzle<String> future = invoke(() -> {
            try {
                Thread.sleep(10000);
                return "I am finished.";
            } catch (InterruptedException e) {
                return "Error";
            }
        });

        System.out.println(future.get());
        System.out.println(future.get());
        System.out.println(future.get());
        //.....
        //....
        while (!future.isDone()) {
            Thread.sleep(10);
        }
        System.out.println(future.get());

        // 阻断式，立刻执行
        String value = block(() -> {
            try {
                Thread.sleep(10000);
                return "I am finished.";
            } catch (InterruptedException e) {
                return "Error";
            }
        });
        System.out.println(value);
    }

    private static <T> T block(CallableDrizzle<T> callable) {
        return callable.action();
    }

    /**
     * 调用callable并立刻返回future
     *
     * @param callable
     * @param <T>
     * @return
     */
    private static <T> FutureDrizzle<T> invoke(CallableDrizzle<T> callable) {

        // 原子
        AtomicReference<T> result = new AtomicReference<>();

        AtomicBoolean finished = new AtomicBoolean(false);
        Thread t = new Thread(() -> {
            T value = callable.action();
            result.set(value);
            finished.set(true);
        });
        t.start();

        return new FutureDrizzle<T>() {
            @Override
            public T get() {
                return result.get();
            }

            @Override
            public boolean isDone() {
                return finished.get();
            }
        };
    }
}
