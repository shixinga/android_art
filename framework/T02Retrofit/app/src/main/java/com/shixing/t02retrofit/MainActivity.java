package com.shixing.t02retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.shixing.t02retrofit.official.NetWorkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    /**
     * 声明请求的接口
     */
    public static NetWorkService netWorkService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void official(View view) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl("https://blog.csdn.net/ysb794008002/article/details/")
                .build();
        netWorkService = retrofit.create(NetWorkService.class);//返回的是代理对象
        Log.d(TAG, "official: NetWorkService=" + netWorkService.getClass());
        Call<String> call = netWorkService.get();
        Log.d(TAG, "official: call=" + call.getClass());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d(TAG, "onResponse: " + response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }
}
