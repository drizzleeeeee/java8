package com.drizzle.java8.completable;

import com.drizzle.java8.future.CallableDrizzle;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Description: Future自实现测试类
 * @Author Administrator
 * @Date 2020/11/21 14:47
 */
public class CompletableFutureDrizzleInAction {

    public static void main(String[] args) throws InterruptedException {

        // 调用
        CompletableFutureDrizzle<String> future = invoke(() -> {
            try {
                Thread.sleep(10000);
                return "I am finished.";
            } catch (InterruptedException e) {
                return "Error";
            }
        });

        future.setCompletable(new CompletableDrizzle<String>() {
            @Override
            public void complete(String s) {
                System.out.println(s);
            }

            @Override
            public void exception(Throwable cause) {
                System.out.println("error");
                cause.printStackTrace();
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
    private static <T> CompletableFutureDrizzle<T> invoke(CallableDrizzle<T> callable) {

        // 原子
        AtomicReference<T> result = new AtomicReference<>();

        AtomicBoolean finished = new AtomicBoolean(false);

        CompletableFutureDrizzle<T> completableFutureDrizzle = new CompletableFutureDrizzle<T>() {

            private CompletableDrizzle<T> completableDrizzle;

            @Override
            public T get() {
                return result.get();
            }

            @Override
            public boolean isDone() {
                return finished.get();
            }

            @Override
            public void setCompletable(CompletableDrizzle<T> t) {
                this.completableDrizzle = t;
            }

            @Override
            public CompletableDrizzle<T> getCompletable() {
                return completableDrizzle;
            }
        };

        Thread t = new Thread(() -> {
            try {
                T value = callable.action();
                result.set(value);

                if (completableFutureDrizzle.getCompletable() != null) {
                    completableFutureDrizzle.getCompletable().complete(value);
                }
            } catch (Exception e) {

                if (completableFutureDrizzle.getCompletable() != null) {
                    completableFutureDrizzle.getCompletable().exception(e);
                }
            }
        });

        t.start();

        return completableFutureDrizzle;
    }
}
