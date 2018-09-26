package com.shixing.myrxjava;

/**
 * Created by shixing on 2018/8/19.
 */

public interface Action<T> {
    //呼唤铁匠打铁
    void call(T t);
}
