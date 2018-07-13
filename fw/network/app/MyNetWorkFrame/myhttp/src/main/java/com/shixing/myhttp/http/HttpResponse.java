package com.shixing.myhttp.http;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by shixing on 2018/7/9.
 */

public interface HttpResponse extends Header, Closeable {

    HttpStatus getStatus();

    String getStatusMsg();

    InputStream getBody() throws IOException;

    void close();

    long getContentLength();
}
