package com.drizzle.java8.future;

/**
 * @Description: 自定义回调接口
 * @Author Drizzle
 * @Date 2020/11/21 14:49
 */
public interface CallableDrizzle<T> {

    /**
     * 做事情
     *
     * @return
     */
    T action();
}