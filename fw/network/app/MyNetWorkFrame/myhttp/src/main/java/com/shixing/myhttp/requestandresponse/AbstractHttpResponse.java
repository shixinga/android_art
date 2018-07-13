package com.shixing.myhttp.requestandresponse;

import com.shixing.myhttp.http.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

/**
 * Created by shixing on 2018/7/9.
 */

public abstract class AbstractHttpResponse implements HttpResponse{
    private static final String GZIP = "gzip";

    private InputStream mGzipInputStream;


    @Override
    public InputStream getBody() throws IOException {
        InputStream bodyIs = getBodyInternal();
        if (isGzip()) {
            return getBodyGzip(bodyIs);
        }
        return bodyIs;
    }

    private InputStream getBodyGzip(InputStream bodyIs) throws IOException {
        if (this.mGzipInputStream == null) {
            this.mGzipInputStream = new GZIPInputStream(bodyIs);
        }
        return mGzipInputStream;
    }
    @Override
    public void close() {

        if (mGzipInputStream != null) {
            try {
                mGzipInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            closeInternal();
        }
    }


    private boolean isGzip() {
        String contentEncoding = getHeaders().getContentEncoding();

        if (GZIP.equals(contentEncoding)) {
            return true;
        }

        return false;
    }

    protected abstract InputStream getBodyInternal() throws IOException;

    protected abstract void closeInternal();
}
