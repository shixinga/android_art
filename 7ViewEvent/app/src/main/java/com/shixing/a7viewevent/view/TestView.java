package com.shixing.a7viewevent.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.TextView;

/**
 * Created by shixing on 2017/9/12.
 */

public class TestView extends android.support.v7.widget.AppCompatTextView {
    private static final String TAG = "TestView";
    int mScaleTouchSlop;
    int mLastX;
    int mLastY;
    public TestView(Context context) {
        super(context);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData(context);
    }

    private void initData(Context context) {
        mScaleTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        Log.d(TAG, "initData: mScaleTouchSlop=" + mScaleTouchSlop);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Thread.dumpStack();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - mLastX;
                int offsetY = y - mLastY;
                scrollBy(-offsetX,-offsetY);
                Log.d(TAG, "onTouchEvent: mScrollX=" + getScrollX() + " mScrollY=" + getScrollY());
                break;

            case MotionEvent.ACTION_UP:

                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }
}
