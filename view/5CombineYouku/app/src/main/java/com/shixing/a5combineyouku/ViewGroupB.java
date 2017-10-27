package com.shixing.a5combineyouku;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by shixing on 2017/10/17.
 */

public class ViewGroupB extends RelativeLayout {
    public ViewGroupB(Context context) {
        super(context);
    }

    public ViewGroupB(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewGroupB(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(MainActivity.TAG, "onMeasure: B  ");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(MainActivity.TAG, "onMeasure:B after ");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(MainActivity.TAG, "onLayout: B ");
        super.onLayout(changed, l, t, r, b);
        Log.d(MainActivity.TAG, "onLayout:  B after");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(MainActivity.TAG, "onDraw: B ");
        super.onDraw(canvas);
        Log.d(MainActivity.TAG, "onDraw:  B after");
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(MainActivity.TAG, "dispatchTouchEvent: B ");
        boolean flag = super.dispatchTouchEvent(ev);
        Log.d(MainActivity.TAG, "dispatchTouchEvent: B after " + flag);
        return flag;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(MainActivity.TAG, "onInterceptTouchEvent: B ");
        boolean flag = super.onInterceptTouchEvent(ev);
        Log.d(MainActivity.TAG, "onInterceptTouchEvent: B after " + flag);
        return flag;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(MainActivity.TAG, "onTouchEvent: B ");
        boolean flag = super.onTouchEvent(event);
        Log.d(MainActivity.TAG, "onTouchEvent: B after " + flag);
        return flag;
    }

}
