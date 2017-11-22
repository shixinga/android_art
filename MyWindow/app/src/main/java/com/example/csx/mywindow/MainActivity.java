package com.example.csx.mywindow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "main onCreate: getContext()=" + getApplicationContext() + " getBaseContext()=" + getBaseContext() + " this=" + this + " getApplication=" + getApplication());
    }

    public void floatActivityClick(View view) {
        Intent intent = new Intent(this, FloatButtonActivity.class);
        startActivity(intent);
    }

    public void dialogClick(View view) {
        Intent intent = new Intent(this, DialogActivity.class);
        startActivity(intent);
    }
}
