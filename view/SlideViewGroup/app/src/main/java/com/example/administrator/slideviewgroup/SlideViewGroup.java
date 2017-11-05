package com.example.administrator.slideviewgroup;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Scroller;

/**
 * Created by Administrator on 2017/10/29.
 */

public class SlideViewGroup extends ViewGroup {

    LinearLayout mLl_left_menu;
    LinearLayout mLl_main_content;

//    int mWidthLeftMenu;
    int mWidthMainContent;
    int mMeasuredHeight;
    int mLastX;

    int mCurrentState = MAIN_CONTENT;
    public static final int MAIN_CONTENT = 1;
    public static final int MENU_LEFT = 2;
    Scroller mScroller;

    int mLastInterceptX;
    int mLastInterceptY;

    public SlideViewGroup(Context context) {
        super(context);
        init();
    }

    public SlideViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlideViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Log.d(MainActivity.TAG, "init: " + getChildCount());
        mScroller = new Scroller(getContext());

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        initView();
        Log.d(MainActivity.TAG, "onMeasure: " + getChildCount());
//        Log.d(MainActivity.TAG, "onMeasure: mLl_left_menu.getLayoutParams().width"
//                + mLl_left_menu.getLayoutParams().width + " getMeasuredHeight()=" + mLl_left_menu.getMeasuredHeight());
        mLl_left_menu.measure(mLl_left_menu.getLayoutParams().width,heightMeasureSpec);

        mLl_main_content.measure(widthMeasureSpec,heightMeasureSpec);


        mWidthMainContent = mLl_main_content.getMeasuredWidth();
        mMeasuredHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(mWidthMainContent, mMeasuredHeight);

    }

    private void initView() {
        mLl_main_content = (LinearLayout) findViewById(R.id.ll_main_content);
        mLl_left_menu = (LinearLayout) findViewById(R.id.ll_left_menu);
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(MainActivity.TAG, "onLayout: ");
        mLl_left_menu.layout(-mLl_left_menu.getLayoutParams().width,0,0,mMeasuredHeight);
        mLl_main_content.layout(0,0,mWidthMainContent,mMeasuredHeight);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        int x = (int)ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - mLastInterceptX;
                int offsetY = y - mLastInterceptY;
                if (Math.abs(offsetX) > Math.abs(offsetY)) {
                    intercepted = true;
                } else {
                    intercepted = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
        }
        mLastInterceptX = x;
        mLastInterceptY = y;
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Log.d(MainActivity.TAG, "onTouchEvent: " + getScrollX());
        int currentX = (int) event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = currentX - mLastX;
                if (getScrollX() - offsetX < -mLl_left_menu.getMeasuredWidth()) {
                    scrollTo(-mLl_left_menu.getMeasuredWidth(),0);
                } else if (getScrollX() -offsetX > 0) {
                    scrollTo(0,0);
                }  else {
                    scrollBy(-offsetX,0);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (getScrollX() > -mLl_left_menu.getLayoutParams().width / 2.0f) {
//                    scrollTo(0,0);
                    mCurrentState = MAIN_CONTENT;
                    updateCurrentContent();
                } else {
//                    scrollTo(-mLl_left_menu.getLayoutParams().width,0);
                    mCurrentState = MENU_LEFT;
                    updateCurrentContent();
                }
                break;
        }
        mLastX = currentX;
        return true;
    }

    private void updateCurrentContent() {
        switch (mCurrentState) {
            case MAIN_CONTENT:
                scrollToMainContent();
                break;
            case MENU_LEFT:
                scrollToMenu();
                break;
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            invalidate();
        }
    }

    private void scrollToMenu() {
        int offsetX = -(mLl_left_menu.getLayoutParams().width + getScrollX());
        mScroller.startScroll(getScrollX(),0,offsetX,0,0);
        invalidate();
    }

    private void scrollToMainContent() {
        int offsetX = -getScrollX();
        mScroller.startScroll(getScrollX(),0,offsetX,0);
        invalidate();
    }

    public void switchContent() {
        if (mCurrentState == MAIN_CONTENT) {
            mCurrentState = MENU_LEFT;
            mScroller.startScroll(0,0,-mLl_left_menu.getLayoutParams().width,0,500);
        } else if (mCurrentState == MENU_LEFT) {
            mCurrentState = MAIN_CONTENT;
            mScroller.startScroll(-mLl_left_menu.getLayoutParams().width,0,mLl_left_menu.getLayoutParams().width ,0,500);
        }
        invalidate();
    }
}
