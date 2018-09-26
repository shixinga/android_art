package com.shixing.dagger2learn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import javax.inject.Inject;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    //添加@Inject注解，表示这个mPoetry是需要注入的
    @Inject
    Poetry mPoetry;

    private TextView mTextView;
    
    @Inject
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 使用Dagger2生成的类 生成组件进行构造，并注入
        DaggerMyMainComponent.builder()
                .build()
                .injectaaa(this);
        initView();
        Log.d(TAG, "onCreate: gson=" + gson);
    }

    private void initView() {
        mTextView = (TextView) findViewById(R.id.tv_poetry);
        mTextView.setText(mPoetry.getPemo());
    }
}
