package one.com.v1view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by shixing on 2017/6/20.
 */

public class DispatchLayoutA extends LinearLayout {
    public DispatchLayoutA(Context context) {
        super(context);
    }

    public DispatchLayoutA(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DispatchLayoutA(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DispatchLayoutA(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(SecondActivity.TAG, "dispatchTouchEventA: " + super.dispatchTouchEvent(ev) +"="+ ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(SecondActivity.TAG, "onInterceptTouchEventA: " + super.onInterceptTouchEvent(ev) +"="+ ev.getAction());
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(SecondActivity.TAG, "onTouchEventA: " + super.onTouchEvent(event) +"="+ event.getAction());
        return super.onTouchEvent(event);
    }
}
