package com.shixing.myhttp.requestandresponse.myokhttp;

import com.shixing.myhttp.http.HttpHeader;
import com.shixing.myhttp.http.HttpStatus;
import com.shixing.myhttp.requestandresponse.AbstractHttpResponse;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Response;

/**
 * Created by shixing on 2018/7/9.
 */

public class MyOkHttpResponse extends AbstractHttpResponse {
    private Response mResponse;
    private HttpHeader mHeaders;

    public MyOkHttpResponse(Response mResponse) {
        this.mResponse = mResponse;
    }

    @Override
    public HttpHeader getHeaders() {
        if (mHeaders == null) {
            mHeaders = new HttpHeader();
        }
        for (String name : mResponse.headers().names()) {
            mHeaders.set(name, mResponse.headers().get(name));
        }
        return mHeaders;
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.getValue(mResponse.code());
    }

    @Override
    public String getStatusMsg() {
        return mResponse.message();
    }

    @Override
    public long getContentLength() {
        return mResponse.body().contentLength();
    }

    @Override
    protected InputStream getBodyInternal() throws IOException {
        return mResponse.body().byteStream();
    }

    @Override
    protected void closeInternal() {
        mResponse.body().close();
    }
}
