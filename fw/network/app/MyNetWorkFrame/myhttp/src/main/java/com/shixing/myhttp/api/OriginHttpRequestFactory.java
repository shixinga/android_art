package com.shixing.myhttp.api;

import com.shixing.myhttp.http.HttpMethod;
import com.shixing.myhttp.http.HttpRequest;
import com.shixing.myhttp.requestandresponse.myoriginal.OriginHttpRequest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;

/**
 * Created by shixing on 2018/7/9.
 */

public class OriginHttpRequestFactory implements HttpRequestFactory {
    private HttpURLConnection mConnection;

    public OriginHttpRequestFactory() {

    }

    public void setReadTimeOut(int readTimeOut) {
        mConnection.setReadTimeout(readTimeOut);
    }

    public void setConnectionTimeOut(int connectionTimeOut) {
        mConnection.setConnectTimeout(connectionTimeOut);
    }

    @Override
    public HttpRequest createHttpRequest(URI uri, HttpMethod method) throws IOException {
        mConnection = (HttpURLConnection) uri.toURL().openConnection();
        return new OriginHttpRequest(mConnection, method, uri.toString());
    }
}
