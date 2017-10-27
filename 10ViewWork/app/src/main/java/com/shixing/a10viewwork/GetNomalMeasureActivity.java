package com.shixing.a10viewwork;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by shixing on 2017/9/15.
 */

public class GetNomalMeasureActivity extends Activity {

    Button mButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nomalmeasure);
        mButton = (Button) findViewById(R.id.bt_nomalmeasure);
        measureView();
    }

    //getMeasureHeight()方式1（没效果！！）：
    private void measureView() {
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec((1 << 30) - 1, View.MeasureSpec.AT_MOST);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(100, View.MeasureSpec.EXACTLY);
        mButton.measure(widthMeasureSpec,heightMeasureSpec); //没反应
        Log.d(MainActivity.TAG, "measureView: widthMeasureSpec=" + widthMeasureSpec +
            " heightMeasureSpec=" + heightMeasureSpec);
    }

    //getMeasureHeight()方式2
    public void nomalmeasure(View view) {
        Log.d(MainActivity.TAG, "nomalmeasure: getWidth()=" + view.getWidth() + " getHeight()=" +view.getHeight());
        Log.d(MainActivity.TAG, "nomalmeasure: getMeasureWidth()=" + view.getMeasuredWidth()
                + " getMeasureHeight()=" + view.getMeasuredHeight());
    }

    //getMeasureHeight()方式3
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.d(MainActivity.TAG, "onWindowFocusChanged: getHeight()=" + mButton.getHeight()
            + " getWidth()=" + mButton.getWidth());
        Log.d(MainActivity.TAG, "onWindowFocusChanged: getMeasuredHeight()=" + mButton.getMeasuredHeight()
            + " getMeasuredWidth()=" + mButton.getMeasuredWidth());
    }

    //getMeasureHeight()方式4
    @Override
    protected void onResume() {
        super.onResume();

        mButton.post(new Runnable() {
            @Override
            public void run() {
                Log.d(MainActivity.TAG, "onResume:mButton1.getMeasuredHeight()= "+mButton.getMeasuredHeight());
            }
        });
    }
}
