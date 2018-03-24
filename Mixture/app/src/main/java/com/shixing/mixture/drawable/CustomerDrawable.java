package com.shixing.mixture.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Administrator on 2017/11/12.
 */

public class CustomerDrawable extends Drawable {
    private static final String TAG = "CustomerDrawable";
    Paint mPaint;

    public CustomerDrawable(int color) {
        Log.d(TAG, "CustomerDrawable: ");
        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);
        
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect rect = getBounds();
        int centerX= rect.centerX();
        int centerY = rect.centerY();
        Log.d(TAG, "draw: centerX=" + centerX + " centerY=" + centerY + " height=" + rect.height() + " width=" + rect.width());
        canvas.drawCircle(centerX,centerY, Math.min(centerX,centerY), mPaint);
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
        Log.d(TAG, "setAlpha: ");
        mPaint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        Log.d(TAG, "setColorFilter: ");
        mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        Log.d(TAG, "getOpacity: ");
        return PixelFormat.TRANSLUCENT;
    }
}
