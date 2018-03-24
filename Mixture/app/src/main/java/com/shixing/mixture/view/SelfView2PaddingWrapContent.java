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

public class SelfView2PaddingWrapContent extends View {
    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public SelfView2PaddingWrapContent(Context context) {
        super(context);
    }

    public SelfView2PaddingWrapContent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SelfView2PaddingWrapContent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        if (mode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(200,MeasureSpec.getSize(heightMeasureSpec));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.RED);
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int height = getHeight() - paddingBottom - paddingTop;
        int width = getWidth();
        int radius = Math.min(height, width) / 2;
        canvas.drawCircle(width/2, paddingTop + height/2,radius,mPaint);

    }
}
