package com.shixing.myhttp.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

/**
 * Created by shixing on 2018/7/9.
 */

public interface HttpRequest extends Header {

    HttpMethod getMethod();

    URI getUri();

    OutputStream getBody();

    HttpResponse execute() throws IOException;
}
