package com.shixing.a7viewevent.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by shixing on 2017/9/14.
 */

public class ListViewExInner extends ListView {
    int mLastX;
    int mLastY;
    HoricentalScrollViewExInner mHorizontalScrollViewExInner;
    public ListViewExInner(Context context) {
        super(context);
    }

    public ListViewExInner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewExInner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setmHorizontalScrollViewExInner(HoricentalScrollViewExInner mHorizontalScrollViewExInner) {
        this.mHorizontalScrollViewExInner = mHorizontalScrollViewExInner;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mHorizontalScrollViewExInner.requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    mHorizontalScrollViewExInner.requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        mLastX = x;
        mLastY = y;
        return super.dispatchTouchEvent(ev);
    }

}
