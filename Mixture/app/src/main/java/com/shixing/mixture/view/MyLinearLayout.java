package com.shixing.mixture.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.shixing.mixture.R;

/**
 * Created by Administrator on 2017/11/4.
 */

public class MyLinearLayout extends LinearLayout {
       private static final String TAG = "MyLinearLayout";

    LinearLayout mLlHeader;
    ImageView mIv;
    //    ListView mLv;
    MyExpandableListView myExpandableListView;
    int mHeaderMeasuredHeight;
    int mOriginalMeasuredHeight;

    int mLastX;
    int mLastY;

    int mTouchSlop;

    public static final int STATE_EXPANDED = 1;
    public static final int STATE_COLLAPSED = 2;
    int mState = STATE_EXPANDED;


    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        mLlHeader = (LinearLayout) findViewById(R.id.ll_header);
        mIv = (ImageView) findViewById(R.id.iv);
        myExpandableListView = (MyExpandableListView) findViewById(R.id.melv);
        mHeaderMeasuredHeight = mLlHeader.getMeasuredHeight();
        mOriginalMeasuredHeight = mHeaderMeasuredHeight;
//        Log.d(TAG, "init: header=" + mLlHeader + " getContext()=" + getContext() + " getContext().getPackageName()=" + getContext().getPackageName());
//        int headerId = getResources().getIdentifier("ll_header","id", getContext().getPackageName());
//        Log.d(TAG, "init: R.id=" + R.id.ll_header + " headerId=" + headerId);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
//        Log.d(TAG, "onWindowFocusChanged: hasWindowFocus=" + hasWindowFocus);
        if (hasWindowFocus && (mLlHeader == null)) {
            init();
//            Log.d(TAG, "onWindowFocusChanged: hasWindowFocus=" + hasWindowFocus + " headerMeasuredHeight=" + mHeaderMeasuredHeight);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onInterceptTouchEvent: " + ev.getAction());
        int intercepted = 0;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercepted = 0;
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - mLastX;
                int offsetY = y - mLastY;
                if (Math.abs(offsetX) > Math.abs(offsetY)) {
                    intercepted = 0;
                } else if (mState == STATE_EXPANDED && offsetY <= -mTouchSlop){
                    intercepted = 1;
                } else if (mState == STATE_EXPANDED && offsetY >= mTouchSlop) {
                    intercepted = 1;
                } else if (myExpandableListView.getFirstVisiblePosition() == 0 && mState == STATE_COLLAPSED && offsetY >= mTouchSlop) {
                    intercepted = 1;
                }

                break;
            case MotionEvent.ACTION_UP:
                intercepted = 0;
                break;
        }

        return intercepted != 0;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        Log.d(TAG, "onTouchEvent: " + event.getAction());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                mLastY = y; //根本就调用不到，因为只要一拦截，那action就是move了
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetY= y - mLastY;
                mHeaderMeasuredHeight += offsetY;
                setMeasuredHeight(mHeaderMeasuredHeight);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        mLastY = y;
        return true;
    }

    private void setMeasuredHeight(int height) {
        if (height <= 0) {
            height = 0;
        }
        if (height>= mOriginalMeasuredHeight) {
            height = mOriginalMeasuredHeight;
        }

        if (height == 0) {
            mState = STATE_COLLAPSED;
        } else {
            mState = STATE_EXPANDED;
        }

        if (mLlHeader != null && mLlHeader.getLayoutParams() != null) {
            mLlHeader.getLayoutParams().height = height;
            mLlHeader.requestLayout();
//            invalidate();
            mHeaderMeasuredHeight = height;
        }
    }

    /*@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mLlHeader != null) {

            Log.d(TAG, "onMeasure: height=" + mLlHeader.getMeasuredHeight() + " width=" + mLlHeader.getMeasuredWidth() + " layoutParams.height=" + mLlHeader.getLayoutParams().height
                    + " layoutParams.width=" + mLlHeader.getLayoutParams().width);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (mLlHeader != null) {

            Log.d(TAG, "onLayout: height=" + mLlHeader.getHeight() + " width=" + mLlHeader.getWidth() + " layoutParams.height=" + mLlHeader.getLayoutParams().height
                    + " layoutParams.width=" + mLlHeader.getLayoutParams().width);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw: ");
    }*/
}
