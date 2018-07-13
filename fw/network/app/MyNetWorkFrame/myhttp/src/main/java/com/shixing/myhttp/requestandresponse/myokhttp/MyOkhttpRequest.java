package com.shixing.myhttp.requestandresponse.myokhttp;

import com.shixing.myhttp.http.HttpHeader;
import com.shixing.myhttp.http.HttpMethod;
import com.shixing.myhttp.http.HttpResponse;
import com.shixing.myhttp.requestandresponse.BufferHttpRequest;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by shixing on 2018/7/9.
 */

public class MyOkhttpRequest extends BufferHttpRequest {
    private OkHttpClient mClient;

    private HttpMethod mMethod;

    private String mUrl;

    public MyOkhttpRequest(OkHttpClient client, HttpMethod method, String url) {
        this.mClient = client;
        this.mMethod = method;
        this.mUrl = url;
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
        boolean hasBody = mMethod == HttpMethod.POST;
        RequestBody requestBody = null;
        if (hasBody) {
            requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), data);
        }
        Request.Builder builder = new Request.Builder()
                .url(mUrl)
                .method(mMethod.name(), requestBody);
        Set<Map.Entry<String, String>> entrySet = header.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }
        Response response = mClient.newCall(builder.build()).execute();
//        Log.d("main", "executeInternal:content-length= "+ response.body().contentLength());
        System.out.println("executeInternal:content-length= "+ response.body().contentLength());
        return new MyOkHttpResponse(response);
    }
}
