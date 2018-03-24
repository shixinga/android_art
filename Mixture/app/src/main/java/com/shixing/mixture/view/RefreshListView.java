package com.shixing.mixture.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shixing.mixture.MainActivity;
import com.shixing.mixture.R;

import java.text.SimpleDateFormat;

/**
 * Created by shixing on 2017/10/23.
 */

public class RefreshListView extends ListView implements AbsListView.OnScrollListener{
    private View mHeadView;
    float mDownY;
    float mMoveY;
    int mHeaderViewHeight; //目的是让下拉时不卡
    public static final int PULL_TO_REFRESH = 0;

    public static final int RELEASE_REFRESH = 1;
    public static final int REFRESHING = 2;
    int mHeadCurrentState = PULL_TO_REFRESH;

    ImageView mIv_header_arrow;
    ProgressBar mPb_header;
    TextView mTv_header_title;
    TextView mTv_header_desc;

    RotateAnimation mRotateUpAnimation;
    RotateAnimation mRotateDownAnimation;

    IRefreshListener mRefreshListener;

    View mFootView;
    int mFootViewHeight;

    IRefreshFootViewListener mRefreshFootViewListener;

    boolean mIsFootLoadingMore;
    public RefreshListView(Context context) {
        super(context);
        init(context);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        initHeadView(context);

        initRotateAnimation();

        initFootView(context);

    }

    private void initHeadView(Context context) {
        mHeadView = View.inflate(context, R.layout.item_list_f4_header_view,null);
        mPb_header = (ProgressBar) mHeadView.findViewById(R.id.pb_header);
        mIv_header_arrow = (ImageView) mHeadView.findViewById(R.id.iv_header_arrow);
        mTv_header_title = (TextView) mHeadView.findViewById(R.id.tv_header_title);
        mTv_header_desc = (TextView) mHeadView.findViewById(R.id.tv_header_desc);

        int height = mHeadView.getHeight(); //0
//        int measureHeight = mHeadView.getMeasuredHeight(); //0
//        Log.d(MainActivity.TAG, "initHeadView: height=" + height + " measuredHeight=" + measureHeight);
        mHeadView.measure(0,0); //按照mHeadView的布局去测量并得出测量结果的宽高
        height = mHeadView.getHeight();
        mHeaderViewHeight = mHeadView.getMeasuredHeight();
//        Log.d(MainActivity.TAG, "initHeadView:after measure() height=" + height + " measuredHeight=" + mHeaderViewHeight); //0 和110
        mHeadView.setPadding(0,-mHeaderViewHeight,0,0);
        addHeaderView(mHeadView);
    }

    private void initRotateAnimation() {

        mRotateUpAnimation = new RotateAnimation(0,-180,
                RotateAnimation.RELATIVE_TO_SELF,0.5f, RotateAnimation.RELATIVE_TO_SELF,0.5f);
        mRotateUpAnimation.setFillAfter(true);
        mRotateUpAnimation.setDuration(300);
        mRotateDownAnimation = new RotateAnimation(-180,-360,
                RotateAnimation.RELATIVE_TO_SELF,0.5f, RotateAnimation.RELATIVE_TO_SELF,0.5f);
        mRotateDownAnimation.setFillAfter(true);
        mRotateDownAnimation.setDuration(300);
    }

    private void initFootView(Context context) {
        mFootView = View.inflate(context,R.layout.item_list_f4_foot_view,null);
        mFootView.measure(0,0);
        mFootViewHeight = mFootView.getMeasuredHeight();
        mFootView.setPadding(0,-mFootViewHeight,0,0);
        addFooterView(mFootView);
        setOnScrollListener(this);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mMoveY = ev.getY();
                float offsetY = mMoveY - mDownY;

                if (mHeadCurrentState == REFRESHING) {
                    return super.onTouchEvent(ev);//说明在刷新
                }

//                Log.d(MainActivity.TAG, "onTouchEvent: offsetY=" + offsetY + " " + getFirstVisiblePosition());
                if (offsetY > 0 && getFirstVisiblePosition() == 0) {
                    int newPaddingY = (int) (-mHeaderViewHeight + offsetY);
                    mHeadView.setPadding(0, newPaddingY,0,0);
                    if (newPaddingY >=0 && mHeadCurrentState != RELEASE_REFRESH) {
                        Log.d(MainActivity.TAG, "onTouchEvent: 我要变松手刷新" + newPaddingY);
                        mHeadCurrentState = RELEASE_REFRESH;
                        updateHeader();
                    } else if (newPaddingY < 0 && mHeadCurrentState != PULL_TO_REFRESH){
                        Log.d(MainActivity.TAG, "onTouchEvent: 我要变下拉刷新" + newPaddingY);
                        mHeadCurrentState = PULL_TO_REFRESH;
                        updateHeader();
                    }

                }
                break;
            case MotionEvent.ACTION_UP:
                if (mHeadCurrentState == PULL_TO_REFRESH) {
                    mHeadView.setPadding(0,-mHeaderViewHeight,0,0);
                } else if (mHeadCurrentState == RELEASE_REFRESH) {
                    mHeadView.setPadding(0,0,0,0);
                    mHeadCurrentState = REFRESHING;
                    updateHeader();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }


    private void updateHeader() {
        switch (mHeadCurrentState) {
            case PULL_TO_REFRESH:
                mTv_header_title.setText("下拉刷新");
                mIv_header_arrow.startAnimation(mRotateDownAnimation);
                break;
            case RELEASE_REFRESH:
                mTv_header_title.setText("松手刷新");
                mIv_header_arrow.startAnimation(mRotateUpAnimation);
                break;
            case REFRESHING:
                mIv_header_arrow.clearAnimation();
                mIv_header_arrow.setVisibility(View.INVISIBLE);
                mPb_header.setVisibility(View.VISIBLE);
                mRefreshListener.onReFresh();
                break;
        }
    }

    public void afterRefreshing() {
        mHeadCurrentState = PULL_TO_REFRESH;
        mTv_header_title.setText("下拉刷新");
        mIv_header_arrow.setVisibility(View.VISIBLE);
        mPb_header.setVisibility(View.INVISIBLE);
        mHeadView.setPadding(0,-mHeaderViewHeight,0,0);
        mTv_header_desc.setText("最后一次刷新时间:" + getTime());
    }

    private String getTime() {
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        return simpleDateFormat.format(currentTime);
    }

    public interface IRefreshListener {

        void onReFresh();
    }
    public void setmRefreshListener(IRefreshListener mRefreshListener) {
        this.mRefreshListener = mRefreshListener;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mIsFootLoadingMore) {
            return;
        }
//        Log.d(MainActivity.TAG, "onScrollStateChanged: " + scrollState + " getLastVisiblePosition()=" +getLastVisiblePosition()
//            + " getCount()=" + getCount());
        if (getLastVisiblePosition() >= (getCount() - 1) && scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
            mIsFootLoadingMore = true;
            mFootView.setPadding(0,0,0,0);
            setSelection(getCount()); //显示 “加载更多”的view
            Log.d(MainActivity.TAG, "onScrollStateChanged: ");
            mRefreshFootViewListener.refreshFoot();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    public interface IRefreshFootViewListener {
        void refreshFoot();
    }

    public void setmRefreshFootViewListener(IRefreshFootViewListener mRefreshFootViewListener) {
        this.mRefreshFootViewListener = mRefreshFootViewListener;
    }

    public void afterRefreshingFoot() {
        mFootView.setPadding(0,-mFootViewHeight,0,0);
        mIsFootLoadingMore = false;
//        setSelection(getCount());
    }
}
