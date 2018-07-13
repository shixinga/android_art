package com.shixing.mynetworkframe.test;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by shixing on 2018/7/1.
 */

public class UploadFile {

    public static final String PATH = "E:\\csx\\github\\android_art\\fw\\network\\app\\MyNetWorkFrame\\";
    public static void main(String[] args) {

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"),
                new File(PATH + "app\\src\\main\\res\\drawable\\mn2.jpg"));
        MultipartBody multipartBody = new MultipartBody.Builder()

                .setType(MultipartBody.FORM)
                .addFormDataPart("username", "一个帅哥") //增加一个对应的html的<input type="text">字段时，需要添加上面一个代码(setType())!!!

                .addFormDataPart("filename", "mv22.jpg", requestBody) //第一个参数给server找到对应的文件对象Part,第二个参数随便写
                .build();
        Request request = new Request.Builder()
                .method("POST", multipartBody)
                .url("http://localhost:8080/NetworkServer/upload")
                .build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.body().string() + ", contenttype=" + response.body().contentType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
