package com.shixing.myrxjava;

/**
 * Created by shixing on 2018/8/19.
 *  抽象的换的行为
 * T 代表 养羊人的角色
 * R  代表 打铁人的角色
 */

public interface Func<T,R> {
    R call(T t);
}
