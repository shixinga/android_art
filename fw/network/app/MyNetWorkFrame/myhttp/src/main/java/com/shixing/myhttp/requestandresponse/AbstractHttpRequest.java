package com.shixing.myhttp.requestandresponse;

import com.shixing.myhttp.http.HttpHeader;
import com.shixing.myhttp.http.HttpRequest;
import com.shixing.myhttp.http.HttpResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by shixing on 2018/7/9.
 */

public abstract class AbstractHttpRequest implements HttpRequest {

    private static final String GZIP = "gzip";

    private HttpHeader mHeader = new HttpHeader();

    private ZipOutputStream mZip;

    private boolean executed;

    @Override
    public HttpHeader getHeaders() {
        return mHeader;
    }



    @Override
    public OutputStream getBody() {
        OutputStream bodyOs = getBodyOutputStream();
        if (isGzip()) {
            return getGzipOutStream(bodyOs);
        }
        return bodyOs;
    }

    private OutputStream getGzipOutStream(OutputStream bodyOs) {
        if (this.mZip == null) {
            this.mZip = new ZipOutputStream(bodyOs);
        }
        return mZip;
    }

    private boolean isGzip() {

        String contentEncoding = getHeaders().getContentEncoding();
        if (GZIP.equals(contentEncoding)) {
            return true;
        }
        return false;
    }
    @Override
    public HttpResponse execute() throws IOException {
        if (mZip != null) {
            mZip.close();
        }
        HttpResponse response = executeInternal(mHeader);
        executed = true;
        return response;
    }

    protected abstract HttpResponse executeInternal(HttpHeader mHeader) throws IOException;

    protected abstract OutputStream getBodyOutputStream();
}
