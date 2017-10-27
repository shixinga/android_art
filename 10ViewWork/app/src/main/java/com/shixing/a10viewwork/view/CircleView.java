package com.shixing.a10viewwork.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.shixing.a10viewwork.MainActivity;
import com.shixing.a10viewwork.R;

/**
 * Created by shixing on 2017/9/15.
 */

public class CircleView extends View {
    int mColor = Color.RED;
    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public CircleView(Context context) {
        super(context);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        mColor = ta.getColor(R.styleable.CircleView_circle_color,Color.GREEN);
        ta.recycle();
        Log.d(MainActivity.TAG, "CircleView: ");
        init();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mPaint.setColor(mColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        //MeasureSpec.AT_MOST对应wrap_content,而不是match_parent
        //将wrap_content的值改变
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(300,300);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(300,heightSize);
            Log.d(MainActivity.TAG, "onMeasure: ");
        } else if (heightMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSize,300);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;
        int radius = Math.min(width,height) / 2;
        Log.d(MainActivity.TAG, "onDraw: width=" + width + " height=" + height);
        canvas.drawCircle(paddingLeft + width / 2,paddingTop + height / 2, radius, mPaint);
    }
    //padding没有效果
   /* @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        int radius = Math.min(width,height) / 2;
        Log.d(MainActivity.TAG, "onDraw: width=" + width + " height=" + height);
        canvas.drawCircle(width / 2,height / 2, radius, mPaint);
    }*/
}
