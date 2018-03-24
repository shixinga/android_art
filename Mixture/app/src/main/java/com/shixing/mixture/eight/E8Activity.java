package com.shixing.mixture.eight;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.shixing.mixture.MainActivity;
import com.shixing.mixture.R;

/**
 * Created by shixing on 2018/2/21.
 */

public class E8Activity extends Activity {
    WindowManager mWindowManager;
    WindowManager.LayoutParams mLayoutParams;
    Button mBtFloat;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_button_e8);
        Log.d(MainActivity.TAG, "second onCreate: getContext()=" + getApplicationContext() + " getBaseContext()=" + getBaseContext() + " this=" + this + " getApplication=" + getApplication());
        init();
    }

    private void init() {
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Log.d(MainActivity.TAG, "init: " + mWindowManager + ",,,," + getWindowManager());


    }


    public void floatBtClick(View view) {
        mBtFloat = new Button(this);
        mBtFloat.setText("i can float");
        mLayoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT, 0, 0, PixelFormat.TRANSPARENT);
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
//        mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        mLayoutParams.x = 100;
        mLayoutParams.y = 300;
        mBtFloat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getRawX();
                int y = (int) event.getRawY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        break;
                    case MotionEvent.ACTION_MOVE:
                        int height = mBtFloat.getHeight();
                        int width = mBtFloat.getWidth();
                        mLayoutParams.x = x - width / 2;
                        mLayoutParams.y = y - height / 2;
                        mWindowManager.updateViewLayout(mBtFloat, mLayoutParams);
                        break;
                    case MotionEvent.ACTION_UP:

                        break;
                }

                return false;
            }
        });

        mWindowManager.addView(mBtFloat, mLayoutParams);

    }


    public void hideFloatButton(View view) {
        mWindowManager.removeView(mBtFloat);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWindowManager.removeView(mBtFloat);
    }
}
