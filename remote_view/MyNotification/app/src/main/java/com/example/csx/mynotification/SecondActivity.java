package com.example.csx.mynotification;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/11/7.
 */

public class SecondActivity extends Activity {
    TextView tv_second;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tv_second = (TextView) findViewById(R.id.tv_second);
        String sid = getIntent().getStringExtra("sid");
        tv_second.setText("second receive sid=" + sid);
    }
}
