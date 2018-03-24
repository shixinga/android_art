package com.shixing.mixture;

import android.app.Application;
import android.util.Log;

/**
 * Created by shixing on 2018/2/17.
 */

public class MyApplication extends Application {
    private static final String TAG = "MainActivity1";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: application");
    }
}
