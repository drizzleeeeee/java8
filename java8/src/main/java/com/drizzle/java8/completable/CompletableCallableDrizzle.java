package com.drizzle.java8.completable;

/**
 * @Description: 自定义回调接口
 * @Author Drizzle
 * @Date 2020/11/21 14:49
 */
public interface CompletableCallableDrizzle<T> {

    /**
     * 做事情
     *
     * @return
     */
    T action();
}