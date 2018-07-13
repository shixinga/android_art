package com.shixing.myhttp.service;

/**
 * Created by shixing on 2018/7/11.
 */

public abstract class MyAbstractResponse<T> {

    public abstract void success(MyRequest request, T data);

    public abstract void fail(int errorCode, String errorMsg);
}
