package csx.haha.com.optimization.s6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import csx.haha.com.optimization.R;
import csx.haha.com.optimization.s4.S4BitmapActivity;

/**
 * Created by sunray on 2018-3-25.
 */

public class S6Avitivity extends Activity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s6);
    }

    public void click1(View view) {
        Intent intent = new Intent(this, KeepAliveAvitivity.class);
        startActivity(intent);
        finish();
    }

    public void click2(View view) {
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "S6Avitivity onStart: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "S6Avitivity onRestart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "S6Avitivity onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "S6Avitivity onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "S6Avitivity onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "S6Avitivity onDestroy: ");
    }
}
