package com.drizzle.java8.completable;

/**
 * @Description: 自定义Future接口
 * @Author Drizzle
 * @Date 2020/11/21 14:49
 */
public interface CompletableFutureDrizzle<T> {

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

    /**
     * 设置回调
     *
     * @param t
     */
    void setCompletable(CompletableDrizzle<T> t);

    /**
     * 获取回调
     */
    CompletableDrizzle<T> getCompletable();
}