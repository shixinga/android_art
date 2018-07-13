package com.shixing.myhttp.requestandresponse.myoriginal;

import com.shixing.myhttp.http.HttpHeader;
import com.shixing.myhttp.http.HttpMethod;
import com.shixing.myhttp.http.HttpResponse;
import com.shixing.myhttp.requestandresponse.BufferHttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Map;

/**
 * Created by shixing on 2018/7/9.
 */

public class OriginHttpRequest extends BufferHttpRequest {

    private HttpURLConnection mConnection;

    private String mUrl;

    private HttpMethod mMethod;

    public OriginHttpRequest(HttpURLConnection connection, HttpMethod method, String url) {
        this.mConnection = connection;
        this.mUrl = url;
        this.mMethod = method;
    }


    @Override
    public HttpMethod getMethod() {
        return mMethod;
    }

    @Override
    public URI getUri() {
        return URI.create(mUrl);
    }

    @Override
    protected HttpResponse executeInternal(HttpHeader header, byte[] data) throws IOException {
        //设置header
        for (Map.Entry<String, String> entry : header.entrySet()) {
            mConnection.addRequestProperty(entry.getKey(), entry.getValue());
        }
        mConnection.setDoOutput(true);
        mConnection.setDoInput(true);
        mConnection.setRequestMethod(mMethod.name());
        mConnection.connect();
        if (data != null && data.length > 0) {
            OutputStream outputStream = mConnection.getOutputStream();
            outputStream.write(data, 0, data.length);
            outputStream.close();
        }
        return new OriginHttpResponse(mConnection);
    }
}
