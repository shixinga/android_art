package com.shixing.optinal;

import android.content.Context;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static Context sContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sContext = this;

    }

    public synchronized void testANR() {
        SystemClock.sleep(30 * 1000);

    }

    //synchronized让initView()一直等待
    public synchronized void initView() {

    }

    public void sleep30s(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                testANR();
            }
        }).start();

        SystemClock.sleep(1000);
        initView();
    }
}
