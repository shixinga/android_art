package one.com.v1view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import static android.content.ContentValues.TAG;

/**
 * Created by shixing on 2017/6/19.
 */

/**
 * onMeasure()->onLayout->onDraw()
 */
public class MyView extends View {
    public MyView(Context context) {
        super(context);
        Log.d(MainActivity.TAG, "MyView:1 ");

    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d(MainActivity.TAG, "MyView:2 ");
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d(MainActivity.TAG, "MyView: 3");
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        Log.d(MainActivity.TAG, "MyView: 4");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(MainActivity.TAG, "onTouchEvent: " + event.getAction() + ".." + Thread.currentThread().getName());
        return super.onTouchEvent(event);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(MainActivity.TAG, "dispatchTouchEvent: "+event.getAction()+ ".." + Thread.currentThread().getName());
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(MainActivity.TAG, "onMeasure: "+widthMeasureSpec +".."+heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        Log.d(MainActivity.TAG, "measureWidth: " + specMode);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = 200;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result,specSize);
            }
        }

        return  result;
    }

    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        Log.d(MainActivity.TAG, "measureHeight: " + specMode);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = 200;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result,specSize);
            }
        }

        return  result;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(MainActivity.TAG, "onLayout: ");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLUE);
        Log.d(MainActivity.TAG, "onDraw: width=" + getWidth() + "..height=" + getHeight());
    }
}
