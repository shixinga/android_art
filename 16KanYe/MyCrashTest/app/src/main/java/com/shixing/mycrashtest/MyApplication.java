package com.shixing.mycrashtest;

import android.app.Application;
import android.util.Log;

import com.shixing.mycrashtest.crashhandler.MyCrashHandler;

/**
 * Created by shixing on 2017/11/25.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(MainActivity.TAG, "onCreate: this=" + this + " getApplicationContext()=" + getApplicationContext());
        MyCrashHandler.getInstance().init(this);
    }
}
