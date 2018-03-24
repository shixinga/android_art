package com.shixing.mixture.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by shixing on 2018/2/21.
 */

public class SelfView extends View {
    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public SelfView(Context context) {
        super(context);
    }

    public SelfView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SelfView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.RED);
        int height = getHeight();
        int width = getWidth();
        int radius = Math.min(height, width) / 2;
        canvas.drawCircle(width/2, height/2,radius,mPaint);

    }
}
