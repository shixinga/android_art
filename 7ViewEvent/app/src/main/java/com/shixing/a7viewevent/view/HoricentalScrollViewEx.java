package com.shixing.a7viewevent.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * 外部拦截法
 * Created by shixing on 2017/9/13.
 */

public class HoricentalScrollViewEx extends ViewGroup {
    private static final String TAG = "HoricentalScrollViewEx";

    private int mChildrenSize;
    private int mChildWidth;
    private int mChildIndex;
    int mLastX;
    int mLastY;
    VelocityTracker mVelocityTracker;
    Scroller mScroller;

    public HoricentalScrollViewEx(Context context) {
        super(context);
        init();
    }

    public HoricentalScrollViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HoricentalScrollViewEx(Context context, AttributeSet attrs,
                                  int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onInterceptTouchEvent: ");
        boolean isIntercept = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onInterceptTouchEvent:down ");
                isIntercept = false; //if true,则所有的事件都被该组件拦截，此时子view的点击事件就不起作用
                if (!mScroller.isFinished()) { //mScroller.abortAnimation()是为了优化滑动体验的
                    mScroller.abortAnimation();
                    isIntercept = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onInterceptTouchEvent: move");
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    isIntercept = true;
                } else {
                    isIntercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "onInterceptTouchEvent:up ");
                isIntercept = false; //if 为true，子view的点击事件就不起作用
                break;
        }
        mLastX = x;
        mLastY = y;
        return isIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onTouchEvent:down "); //mScroller.abortAnimation()是为了优化滑动体验的
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onTouchEvent: move");
                int deltaX = x - mLastX;
                scrollBy(-deltaX,0);
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
        mLastY = y;
        mLastX = x;
        return true;
    }

    private void smoothScrollBy(int dx) {
        mScroller.startScroll(getScrollX(),0,dx,0,600);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        Log.d(TAG, "onMeasure: "+getChildCount() + " witdh=" +  MeasureSpec.getSize(widthMeasureSpec) + " height=" + MeasureSpec.getSize(heightMeasureSpec));
        measureChildren(widthMeasureSpec, heightMeasureSpec); //给子view设置宽和高，缺了它就没有显示了
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec) * childCount;
        setMeasuredDimension(measureWidth,MeasureSpec.getSize(heightMeasureSpec));

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "onLayout: ");
        int childLeft = 0;
        final int childCount = getChildCount();
        mChildrenSize = childCount;
        for (int i = 0; i < mChildrenSize; ++i) {
            View childView = getChildAt(i);
            Log.d(TAG, "onLayout: getWidth()=" + childView.getWidth() + " getMeasuredWidth()" + childView.getMeasuredWidth());
            final int childWidth = childView.getMeasuredWidth();
            mChildWidth = childWidth;
            childView.layout(childLeft,0,childView.getMeasuredWidth() + childLeft, childView.getMeasuredHeight());
            childLeft += childView.getMeasuredWidth();
        }

    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }
}
