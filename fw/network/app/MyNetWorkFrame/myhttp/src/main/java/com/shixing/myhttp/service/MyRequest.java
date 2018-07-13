package com.shixing.myhttp.service;

import com.shixing.myhttp.http.HttpMethod;

/**
 * Created by shixing on 2018/7/11.
 */

public class MyRequest {

    private String mUrl;

    private HttpMethod mMethod;

    private byte[] mData;

    private MyAbstractResponse mResponse;

    private String mContentType;

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public HttpMethod getMethod() {
        return mMethod;
    }

    public void setMethod(HttpMethod method) {
        mMethod = method;
    }

    public byte[] getData() {
        return mData;
    }

    public void setData(byte[] data) {
        mData = data;
    }

    public MyAbstractResponse getResponse() {
        return mResponse;
    }

    public void setResponse(MyAbstractResponse response) {
        mResponse = response;
    }

    public String getContentType() {
        return mContentType;
    }

    public void setContentType(String contentType) {
        mContentType = contentType;
    }

}
