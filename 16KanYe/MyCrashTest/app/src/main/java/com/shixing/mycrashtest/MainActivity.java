package com.shixing.mycrashtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void crashClick(View view) {
        throw new RuntimeException("你大哥自己定义的异常，快给你大哥打印出来！！！");
    }
}
