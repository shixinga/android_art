package com.shixing.a8motionevent.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.TextView;

import com.shixing.a8motionevent.MainActivity;

/**
 * Created by shixing on 2017/9/13.
 */

public class ViewC extends TextView {
    public ViewC(Context context) {
        super(context);
    }

    public ViewC(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewC(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(MainActivity.TAG, "C dispatchTouchEvent: ");
        boolean flagC = super.dispatchTouchEvent(event);
        return flagC;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(MainActivity.TAG, "C onTouchEvent: ");
        boolean flagC = super.onTouchEvent(event);
        Log.d(MainActivity.TAG, "C onTouchEvent: flagC=" + flagC);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                return true; //如果ACTION_DOWN被该view（或viewGroup）处理，则剩下的事件都是该view处理，而不会再调用其父元素的onTouchEvent()
                             //而如果ACTION_DOWN不被该view(或viewGroup)处理，则剩下的事件都不会传给该view（或viewGroup）处理
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:

                break;

        }
        return flagC;
    }
}
