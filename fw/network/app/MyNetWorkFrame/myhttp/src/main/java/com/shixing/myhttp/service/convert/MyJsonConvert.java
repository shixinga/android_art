package com.shixing.myhttp.service.convert;

import com.google.gson.Gson;
import com.shixing.myhttp.http.HttpResponse;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;

/**
 * Created by shixing on 2018/7/11.
 */

public class MyJsonConvert implements IConvert {
    private Gson gson = new Gson();

    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    @Override
    public Object parse(HttpResponse response, Type type) throws IOException {
        Reader reader = new InputStreamReader(response.getBody());
        return gson.fromJson(reader, type);
    }

    @Override
    public boolean isCanParse(String contentType) {
        return CONTENT_TYPE.equals(contentType);
    }

    @Override
    public Object parse(String content, Type type) throws IOException {
        return gson.fromJson(content, type);
    }
}
