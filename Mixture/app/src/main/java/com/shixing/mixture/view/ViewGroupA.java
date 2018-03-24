package com.shixing.mixture.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.shixing.mixture.MainActivity;

/**
 * Created by shixing on 2017/10/17.
 */

public class ViewGroupA extends RelativeLayout {
    public ViewGroupA(Context context) {
        super(context);
    }

    public ViewGroupA(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewGroupA(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(MainActivity.TAG, "onMeasure: A  ");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(MainActivity.TAG, "onMeasure: A after ");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(MainActivity.TAG, "onLayout: A ");
        super.onLayout(changed, l, t, r, b);
        Log.d(MainActivity.TAG, "onLayout:  A after");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(MainActivity.TAG, "onDraw: A ");
        super.onDraw(canvas);
        Log.d(MainActivity.TAG, "onDraw:  A after");
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(MainActivity.TAG, "dispatchTouchEvent: A ");
        boolean flag = super.dispatchTouchEvent(ev);
        Log.d(MainActivity.TAG, "dispatchTouchEvent: A after " + flag);
        return flag;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(MainActivity.TAG, "onInterceptTouchEvent: A ");
        boolean flag = super.onInterceptTouchEvent(ev);
        Log.d(MainActivity.TAG, "onInterceptTouchEvent: A after " + flag);
        return flag;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(MainActivity.TAG, "onTouchEvent: A ");
        boolean flag = super.onTouchEvent(event);
        Log.d(MainActivity.TAG, "onTouchEvent: A after " + flag);
        return flag;
    }
}
