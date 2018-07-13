package com.shixing.myhttp.api;

import com.shixing.myhttp.http.HttpMethod;
import com.shixing.myhttp.http.HttpRequest;

import java.io.IOException;
import java.net.URI;

/**
 * Created by shixing on 2018/7/9.
 */

public interface HttpRequestFactory {

    HttpRequest createHttpRequest(URI uri, HttpMethod method) throws IOException;
}
