package com.shixing.mynetworkframe.test;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by shixing on 2018/7/1.
 *
 * 使用okhttp的cache的前提条件是服务器返回的页面支持缓存，即服务器的相应界面的cache-control != no-cache
 *
 第2次run的result:
 response.networkResponse()=null
 response.cacheResponse()=Response{protocol=http/1.1, code=200, message=OK, url=http://www.qq.com/}
 =============second request==========
 response.networkResponse()=null
 response.cacheResponse()=Response{protocol=http/1.1, code=200, message=OK, url=http://www.qq.com/}

 */

public class CacheFile {
    public static final String PATH = "E:\\csx\\github\\android_art\\fw\\network\\app\\MyNetWorkFrame\\test\\cachedir\\";
    public static void main(String[] args) {

        int maxSize = 10 * 1024 * 1024;
        Cache cache = new Cache(new File(PATH), maxSize);
        OkHttpClient client = new OkHttpClient.Builder()
                .cache(cache)
                .build();
        Request request = new Request.Builder()
                //http://www.qq.com 的界面支持缓存
                .url("http://www.qq.com")
                //https://www.imooc.com/的界面不支持缓存
//                .url("https://www.imooc.com")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String body1 = response.body().string();
            System.out.println("response.networkResponse()=" + response.networkResponse());
            System.out.println("response.cacheResponse()=" + response.cacheResponse());

            Response response2 = client.newCall(request).execute();
            String body2 = response2.body().string();
            System.out.println("=============second request==========");
            System.out.println("response.networkResponse()=" + response.networkResponse());
            System.out.println("response.cacheResponse()=" + response.cacheResponse());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
