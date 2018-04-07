package com.shixing.classloader;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by shixing on 2018/4/6.
 */

public class S2 extends Activity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s2);
        Log.d(TAG, "s2 onCreate: baseContext= " + getBaseContext());
        Log.d(TAG, "s2 onCreate: resources = " + getResources());
    }
}
