package one.com.v1view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by shixing on 2017/6/20.
 */

public class DispatchLayoutB extends LinearLayout {
    public DispatchLayoutB(Context context) {
        super(context);
    }

    public DispatchLayoutB(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DispatchLayoutB(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DispatchLayoutB(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(SecondActivity.TAG, "dispatchTouchEventB: " + super.dispatchTouchEvent(ev) +"="+ ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(SecondActivity.TAG, "onInterceptTouchEventB: " + super.onInterceptTouchEvent(ev) +"="+ ev.getAction());
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(SecondActivity.TAG, "onTouchEventB: " + super.onTouchEvent(event) +"="+ event.getAction());
        return super.onTouchEvent(event);
    }
}
