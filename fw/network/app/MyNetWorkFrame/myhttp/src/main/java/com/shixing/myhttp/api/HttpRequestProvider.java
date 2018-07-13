package com.shixing.myhttp.api;

import com.shixing.myhttp.http.HttpMethod;
import com.shixing.myhttp.http.HttpRequest;
import com.shixing.myhttp.utils.Utills;

import java.io.IOException;
import java.net.URI;

public class HttpRequestProvider {

    private static boolean OKHTTP_REQUEST = Utills.isClassExist("okhttp3.OkHttpClient", HttpRequestProvider.class.getClassLoader());

    private HttpRequestFactory mHttpRequestFactory;

    public HttpRequestProvider() {
        if (OKHTTP_REQUEST) {
            mHttpRequestFactory = new OkHttpRequestFactory();
        } else {
            mHttpRequestFactory = new OriginHttpRequestFactory();
        }
    }

    public HttpRequest getHttpRequest(URI uri, HttpMethod httpMethod) throws IOException {
        return mHttpRequestFactory.createHttpRequest(uri, httpMethod);
    }

    public HttpRequestFactory getHttpRequestFactory() {
        return mHttpRequestFactory;
    }

    public void setHttpRequestFactory(HttpRequestFactory httpRequestFactory) {
        mHttpRequestFactory = httpRequestFactory;
    }
}