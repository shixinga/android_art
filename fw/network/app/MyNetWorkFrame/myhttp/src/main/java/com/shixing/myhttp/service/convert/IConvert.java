package com.shixing.myhttp.service.convert;

import com.shixing.myhttp.http.HttpResponse;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by shixing on 2018/7/11.
 */

public interface IConvert {

    Object parse(HttpResponse response, Type type) throws IOException;

    boolean isCanParse(String contentType);

    Object parse(String content, Type type) throws IOException;
}
