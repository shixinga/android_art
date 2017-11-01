package com.shixing.setwifitoprint;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.shixing.setwifitoprint.utils.NetworkSetting;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread() {
            @Override
            public void run() {
                Log.d(TAG, "run: " + NetworkSetting.postData());
                Log.d(TAG, "run: " + NetworkSetting.putMyData());
            }
        }.start();
    }
}
