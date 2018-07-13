package com.shixing.myhttp.requestandresponse.myoriginal;

import com.shixing.myhttp.http.HttpHeader;
import com.shixing.myhttp.http.HttpStatus;
import com.shixing.myhttp.requestandresponse.AbstractHttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created by shixing on 2018/7/9.
 */

public class OriginHttpResponse extends AbstractHttpResponse {
    private HttpURLConnection mConnection;

    public OriginHttpResponse(HttpURLConnection connection) {
        this.mConnection = connection;
    }

    @Override
    public HttpHeader getHeaders() {

        HttpHeader header = new HttpHeader();
        for (Map.Entry<String, List<String>> entry : mConnection.getHeaderFields().entrySet()) {
            header.set(entry.getKey(), entry.getValue().get(0));
        }
        return header;
    }

    @Override
    public HttpStatus getStatus() {
        try {
            return HttpStatus.getValue(mConnection.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getStatusMsg() {
        try {
            return mConnection.getResponseMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getContentLength() {
        return mConnection.getContentLength();
    }

    @Override
    protected InputStream getBodyInternal() throws IOException {
        return mConnection.getInputStream();
    }

    @Override
    protected void closeInternal() {
        mConnection.disconnect();
    }
}
