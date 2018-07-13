package com.shixing.myhttp.requestandresponse;

import com.shixing.myhttp.http.HttpHeader;
import com.shixing.myhttp.http.HttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by shixing on 2018/7/9.
 */

public abstract class BufferHttpRequest extends AbstractHttpRequest {

    private ByteArrayOutputStream mBaos = new ByteArrayOutputStream();
    @Override
    protected HttpResponse executeInternal(HttpHeader header) throws IOException {
        byte[] data = mBaos.toByteArray();
        return executeInternal(header, data);
    }

    @Override
    protected OutputStream getBodyOutputStream() {
        return mBaos;
    }

    protected abstract HttpResponse executeInternal(HttpHeader header, byte[] data) throws IOException;
}
