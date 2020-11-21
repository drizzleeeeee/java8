package com.drizzle.java8.future;

/**
 * @Description: 自定义Future接口
 * @Author Drizzle
 * @Date 2020/11/21 14:49
 */
public interface FutureDrizzle<T> {

    /**
     * 拿到返回值
     *
     * @return
     */
    T get();

    /**
     * 是否执行完成
     *
     * @return
     */
    boolean isDone();
}