package com.shixing.mixture.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.shixing.mixture.MainActivity;

/**
 * Created by shixing on 2017/10/17.
 */

public class ViewGroupC extends RelativeLayout {
    public ViewGroupC(Context context) {
        super(context);
    }

    public ViewGroupC(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewGroupC(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(MainActivity.TAG, "dispatchTouchEvent: C ");
        boolean flag = super.dispatchTouchEvent(ev);
        Log.d(MainActivity.TAG, "dispatchTouchEvent: C after " + flag);
        return flag;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(MainActivity.TAG, "onInterceptTouchEvent: C ");
        boolean flag = super.onInterceptTouchEvent(ev);
        Log.d(MainActivity.TAG, "onInterceptTouchEvent: C after " + flag);
        return flag;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(MainActivity.TAG, "onTouchEvent: C ");
        boolean flag = super.onTouchEvent(event);
        Log.d(MainActivity.TAG, "onTouchEvent: C after " + flag);
        return flag;
    }
}
