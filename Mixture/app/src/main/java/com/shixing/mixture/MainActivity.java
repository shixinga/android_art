package com.shixing.mixture;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.shixing.mixture.eight.E8Activity;
import com.shixing.mixture.fifth.F5Activity;
import com.shixing.mixture.fourth.F4Activity;
import com.shixing.mixture.ninth.N9Activity;
import com.shixing.mixture.second.S2Activity;
import com.shixing.mixture.seventh.S7Activity;
import com.shixing.mixture.sixth.S6Activity;
import com.shixing.mixture.third.T3Activity;

import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: " + savedInstanceState);
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.first:
                Intent intent = new Intent();
                intent.setAction("com.csx.fitst");
                startActivity(intent);
                break;
            case R.id.second:
                Intent intentSecond = new Intent(this, S2Activity.class);
                startActivity(intentSecond);
                break;
            case R.id.third:
                Intent intentThird = new Intent(this, T3Activity.class);
                startActivity(intentThird);
                break;
            case R.id.fourth:
                Intent intentFourth = new Intent(this, F4Activity.class);
                startActivity(intentFourth);
                break;
            case R.id.fifth:
                Intent intentFifth = new Intent(this, F5Activity.class);
                startActivity(intentFifth);
                break;
            case R.id.six:
                Intent intentsix = new Intent(this, S6Activity.class);
                startActivity(intentsix);
                break;
            case R.id.seven:
                Intent intentseven = new Intent(this, S7Activity.class);
                startActivity(intentseven);
                break;
            case R.id.eight:
                Intent intenteight = new Intent(this, E8Activity.class);
                startActivity(intenteight);
                break;
            case R.id.nine:
                Intent intentnine = new Intent(this, N9Activity.class);
                startActivity(intentnine);
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.d(TAG, "onSaveInstanceState: ");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}
