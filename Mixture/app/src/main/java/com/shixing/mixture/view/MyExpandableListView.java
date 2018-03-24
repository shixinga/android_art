package com.shixing.mixture.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ExpandableListView;

import com.shixing.mixture.MainActivity;

/**
 * Created by Administrator on 2017/11/6.
 */

public class MyExpandableListView extends ExpandableListView implements AbsListView.OnScrollListener {
    View mHeaderView;
    int mMeasuredHeight;
    int mMeasuredWidth;
    IOnHeaderUpdateListener mIOnHeaderUpdateListener;


    public interface IOnHeaderUpdateListener {
        View getHeaderView();

        void updateHeaderView(View view, int firstVisibleGroupPos);
    }



    public MyExpandableListView(Context context) {
        super(context);
        init();
    }

    public MyExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {
        setOnScrollListener(this);
    }

    public void setmIOnHeaderUpdateListener(IOnHeaderUpdateListener mIOnHeaderUpdateListener) {
        this.mIOnHeaderUpdateListener = mIOnHeaderUpdateListener;
        if (mIOnHeaderUpdateListener == null) {
            mHeaderView = null;
            mMeasuredHeight = 0;
            mMeasuredWidth = 0;
            return;
        }
        mHeaderView = mIOnHeaderUpdateListener.getHeaderView();
        int firstVisiblePos = getFirstVisiblePosition();
        int firstVisibleGroupPos = getPackedPositionGroup(getExpandableListPosition(firstVisiblePos));

        Log.d(MainActivity.TAG, "setmIOnHeaderUpdateListener: firstVisiblePos=" + firstVisiblePos + " firstVisibleGroupPos=" + firstVisibleGroupPos);

        mIOnHeaderUpdateListener.updateHeaderView(mHeaderView, firstVisibleGroupPos);
        requestLayout();
//        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mHeaderView == null) {
            return ;
        }
        measureChild(mHeaderView,widthMeasureSpec,heightMeasureSpec);
        mMeasuredHeight = mHeaderView.getMeasuredHeight();
        mMeasuredWidth = mHeaderView.getMeasuredWidth();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (mHeaderView == null) {
            return;
        }
        Log.d(MainActivity.TAG, "onLayout: mHeaderView.getTop()=" + mHeaderView.getTop());
        mHeaderView.layout(0,mHeaderView.getTop(),mMeasuredWidth,mHeaderView.getTop() + mMeasuredHeight);

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mHeaderView != null) {
            drawChild(canvas,mHeaderView,getDrawingTime()); //固定头部位置
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        int pos = pointToPosition(x,y);
        if (mHeaderView  != null && y >= mHeaderView.getTop() && y <= mHeaderView.getBottom()) {
            if (ev.getAction() == MotionEvent.ACTION_UP) {
                int groupPosition = getPackedPositionGroup(getExpandableListPosition(pos));
                if (groupPosition != INVALID_POSITION) {
                    if (isGroupExpanded(groupPosition)) {
                        collapseGroup(groupPosition);
                    } else {
                        expandGroup(groupPosition);
                    }
                }
            }

            return true;
        }
        return super.dispatchTouchEvent(ev);
    }



    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        if (totalItemCount > 0) {
            refreshHeader();
        }
    }

    protected void refreshHeader() {
        if (mHeaderView == null) {
            return;
        }
        int firstVisiblePos = getFirstVisiblePosition();
        int pos = firstVisiblePos + 1;
        int firstVisibleGroupPos = getPackedPositionGroup(getExpandableListPosition(firstVisiblePos));
        int group = getPackedPositionGroup(getExpandableListPosition(pos));
        Log.d(MainActivity.TAG, "refreshHeader firstVisibleGroupPos=" + firstVisibleGroupPos);

        if (group == firstVisibleGroupPos + 1) {
            View view = getChildAt(1);
            if (view == null) {
                Log.w(MainActivity.TAG, "Warning : refreshHeader getChildAt(1)=null");
                return;
            }
            if (view.getTop() <= mMeasuredHeight) {
                int delta = mMeasuredHeight - view.getTop();
                mHeaderView.layout(0, -delta, mMeasuredWidth, mMeasuredHeight - delta);
            } else {
                //TODO : note it, when cause bug, remove it
                mHeaderView.layout(0, 0, mMeasuredWidth, mMeasuredHeight);
            }
        } else {
            mHeaderView.layout(0, 0, mMeasuredWidth, mMeasuredHeight);
        }

        if (mIOnHeaderUpdateListener != null) {
            mIOnHeaderUpdateListener.updateHeaderView(mHeaderView, firstVisibleGroupPos);
        }
    }
}
