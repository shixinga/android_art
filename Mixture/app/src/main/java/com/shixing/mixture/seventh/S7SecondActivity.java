package com.shixing.mixture.seventh;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.shixing.mixture.R;

/**
 * Created by Administrator on 2017/11/13.
 */

public class S7SecondActivity extends Activity {
    private static final String TAG = "SecondActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_s7);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
//        overridePendingTransition(R.anim.anim_enter,R.anim.anim_exit);

        Log.d(TAG, "onDestroy:after ");
    }

    @Override
    public void finish() {
        Log.d(TAG, "finish: ");
        super.finish();
        overridePendingTransition(R.anim.anim_enter,R.anim.anim_exit);
    }
}
