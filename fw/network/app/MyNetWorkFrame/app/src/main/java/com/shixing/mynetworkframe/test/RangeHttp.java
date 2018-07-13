package com.shixing.mynetworkframe.test;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by shixing on 2018/7/3.
 */

public class RangeHttp {

    public static void main(String[] args) {
        Request request = new Request.Builder()
                .url("http://img1.gtimg.com/2018/pics/hv1/54/13/2283/148455444.jpg")
                .addHeader("Range", "bytes=500-888")
                .build();

        OkHttpClient client = new OkHttpClient();
        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.body().contentLength() + ", content-type=" + response.body().contentType());
            if (response.isSuccessful()) {
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); ++i) {
                    System.out.println("name=" + headers.name(i) + ":" + headers.value(i));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
