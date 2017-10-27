package com.shixing.a8switchbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by shixing on 2017/10/21.
 */

public class ToggleView extends View {

    Bitmap mBitmapToggleBackground;
    Bitmap mBitmapSlideView;
    Paint mPaint;
    boolean mSlideState = false;
    boolean mIsSlideMode = false;
    float mCurrentX;
    float mCenterX;
    public ToggleView(Context context) {
        super(context);
    }

    public ToggleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);

    }

    public ToggleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        TypedArray typeArray = context.obtainStyledAttributes(attrs,R.styleable.ToggleView);
        Drawable drawableBackground = typeArray.getDrawable(R.styleable.ToggleView_background);
        Drawable drawableButton = typeArray.getDrawable(R.styleable.ToggleView_src);
        mBitmapToggleBackground = ((BitmapDrawable)drawableBackground).getBitmap();
        mBitmapSlideView = ((BitmapDrawable)drawableButton).getBitmap();
        mCenterX = this.mBitmapToggleBackground.getWidth() / 2.0f;
        typeArray.recycle();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(MainActivity.TAG, "ToggleView onTouchEvent: " + event.getAction());
        float x =  event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mIsSlideMode = true;

                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                mIsSlideMode = false;

                boolean state = x > mCenterX;
                if (mSlideState != state && mOnStateChangedListener != null) {
                    mSlideState = state;
                    mOnStateChangedListener.onStateChanged(state);
                }

                break;
        }
        mCurrentX = x;
        invalidate();
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mBitmapToggleBackground.getWidth(),mBitmapToggleBackground.getHeight());
    }



    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        canvas.drawBitmap(mBitmapToggleBackground,0,0,mPaint);

        if (mIsSlideMode) {
            float newLeft = mCurrentX - mBitmapSlideView.getWidth() / 2.0f;
            if(newLeft > mBitmapToggleBackground.getWidth() - mBitmapSlideView.getWidth()) {
                newLeft = mBitmapToggleBackground.getWidth() - mBitmapSlideView.getWidth();
            } else if (newLeft < 0) {
                newLeft = 0;
            }
            canvas.drawBitmap(mBitmapSlideView,newLeft,0,mPaint);
        } else {
            if (mSlideState) {
                canvas.drawBitmap(mBitmapSlideView,mBitmapToggleBackground.getWidth() - mBitmapSlideView.getWidth(),0,mPaint);
            } else {
                canvas.drawBitmap(mBitmapSlideView,0,0,mPaint);
            }
        }

    }

    public void setmBitmapToggleBackground(int resourceId) {
        this.mBitmapToggleBackground = BitmapFactory.decodeResource(getResources(),resourceId);
        mCenterX = this.mBitmapToggleBackground.getWidth() / 2.0f;
    }

    public void setmBitmapSlideView(int resourceId) {
        this.mBitmapSlideView = BitmapFactory.decodeResource(getResources(),resourceId);;
    }



    /*public void setmSlideState() {
        this.mSlideState = !mSlideState;
        invalidate();
    }*/

    private OnStateChangedListener mOnStateChangedListener;

    public void setmOnStateChangedListener(OnStateChangedListener mOnStateChangedListener) {
        this.mOnStateChangedListener = mOnStateChangedListener;
    }

    public interface OnStateChangedListener {
        void onStateChanged(boolean state);
    }
}
