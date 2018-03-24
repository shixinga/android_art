package com.shixing.mixture.second;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.shixing.mixture.R;

/**
 * Created by shixing on 2018/2/17.
 */

public class S2BActivity extends Activity {

    TextView mTv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s2b);
        mTv = findViewById(R.id.tv_s2_b);
        mTv.setText("新进程的sUid=" + S2Activity.sUid);
    }
}
