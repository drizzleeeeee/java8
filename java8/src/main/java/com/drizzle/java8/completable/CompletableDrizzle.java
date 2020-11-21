package com.drizzle.java8.completable;

public interface CompletableDrizzle<T> {

    /**
     * 完成后回调该方法
     *
     * @param t
     */
    void complete(T t);

    /**
     * 完成过程中出现异常回调该方法
     *
     * @param cause
     */
    void exception(Throwable cause);
}