package com.shixing.a7viewevent.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import static android.R.attr.y;

/**
 * Created by shixing on 2017/9/14.
 */

public class HoricentalScrollViewExInner extends ViewGroup {
    public static final String TAG = "HoricentalScrollViewExI";
    int mLastX;
    int mLastY;
    VelocityTracker mVelocityTracker;
    Scroller mScroller;
    int mChildrenSize;
    private int mChildWidth;
    private int mChildIndex;
    public HoricentalScrollViewExInner(Context context) {
        super(context);
    }

    public HoricentalScrollViewExInner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        mVelocityTracker = VelocityTracker.obtain();
        mScroller = new Scroller(getContext());
    }

    public HoricentalScrollViewExInner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            mLastX = x;
            mLastY = y;
            if (!mScroller.isFinished()) {//mScroller.abortAnimation()是为了优化滑动体验的
                mScroller.abortAnimation();
                return true;
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {//mScroller.abortAnimation()是为了优化滑动体验的
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - mLastX;
                scrollBy(-offsetX,0);
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "onTouchEvent: up");
                //以下是计算是否滑到下一个page，注意mChildWidth是在onLayout()里面赋值的
                int scrollX = getScrollX();
                int scrollToChildIndex = scrollX / mChildWidth;
                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = mVelocityTracker.getXVelocity();
                if (Math.abs(xVelocity) >= 50) {
                    mChildIndex = xVelocity > 0 ? mChildIndex - 1 : mChildIndex + 1;
                } else {
                    mChildIndex = (scrollX + mChildWidth / 2) / mChildWidth;
                }
                mChildIndex = Math.max(0, Math.min(mChildIndex, mChildrenSize - 1));
                int dx = mChildIndex * mChildWidth - scrollX;
                smoothScrollBy(dx);
                mVelocityTracker.clear();
                //以上是计算是否滑到下一个page，注意mChildWidth是在onLayout()里面赋值的
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }

    private void smoothScrollBy(int dx) {
        mScroller.startScroll(getScrollX(),0,dx,0,600);
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        int childCount = getChildCount();
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec) * childCount;
        setMeasuredDimension(measureWidth,MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft = 0;
        mChildrenSize = getChildCount();
        for (int i = 0; i < mChildrenSize; ++i) {
            View childView = getChildAt(i);
            mChildWidth = childView.getMeasuredWidth();
            childView.layout(childLeft,0,mChildWidth+ childLeft,childView.getMeasuredHeight());
            childLeft += mChildWidth;
        }
    }
}
