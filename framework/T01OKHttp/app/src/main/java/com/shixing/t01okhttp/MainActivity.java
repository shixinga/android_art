package com.shixing.t01okhttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    OkHttpClient client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void synRequest() {
        Request request = new Request.Builder().url("http://www.baidu.com")
                .get().build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            Log.d(TAG, "synRequest: " + response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void cacheRequest() {
        Request request = new Request.Builder().url("http://www.baidu.com")
                .get().build();
        OkHttpClient tempClient = new OkHttpClient.Builder().
                cache(new Cache(new File("cache"), 24 * 1024 * 1024)).build();
        Call call = tempClient.newCall(request);
        try {
            Response response = call.execute();
            Log.d(TAG, "synRequest: " + response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void asynRequest() {
        Request request = new Request.Builder().url("http://www.baidu.com")
                .get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + Thread.currentThread().getName());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse: "  + Thread.currentThread().getName()
                    + ", " + response.body().string());
            }
        });
    }
    public void sendSync(View view) {
        new Thread(){
            @Override
            public void run() {
                synRequest();
            }
        }.start();

    }

    public void sendAsync(View view) {
        asynRequest();
    }
}
