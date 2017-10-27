package com.shixing.a6combineadvertisement;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shixing.a6combineadvertisement.adapter.MyViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    ViewPager mViewPager;
    int mImageViewIdList[] ;
    List<ImageView> mImageViewList = new ArrayList<>();
    LinearLayout mLl_circle;
    String mContentDescs[];
    TextView mTv_desc;
    boolean mIsRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mvc的初始化
        initData();

        initView();

        initController();
    }

    private void initData() {
        mImageViewIdList = new int[] {R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e};
        // 文本描述
        mContentDescs = new String[]{
                "巩俐不低俗，我就不能低俗",
                "扑树又回来啦！再唱经典老歌引万人大合唱",
                "揭秘北京电影如何升级",
                "乐视网TV版大派送",
                "热血屌丝的反杀"
        };
    }


    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp);
        mLl_circle = (LinearLayout) findViewById(R.id.ll_circle);
        mTv_desc = (TextView) findViewById(R.id.tv_desc);
        ImageView imageView;

        View viewCircle;
        LinearLayout.LayoutParams viewCircleLayoutParams;
        for (int i = 0; i < mImageViewIdList.length; ++i) {
            imageView = new ImageView(this);
            imageView.setBackgroundResource(mImageViewIdList[i]);
            mImageViewList.add(imageView);

            viewCircle = new View(this);
            viewCircle.setBackgroundResource(R.drawable.selector_bg);
            WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            viewCircleLayoutParams = new LinearLayout.LayoutParams((int)(10 * displayMetrics.density),(int)(10 * displayMetrics.density));
            if (i != 0) {
                viewCircleLayoutParams.leftMargin = 15;
                viewCircle.setEnabled(false);
            }
            mLl_circle.addView(viewCircle,viewCircleLayoutParams);
        }
    }

    private void initController() {
        mViewPager.setAdapter(new MyViewPagerAdapter(mImageViewList));
        mViewPager.setCurrentItem(500000); //设置在中间位置， 这样就可以无限的左右滑动了
        mViewPager.setOnPageChangeListener(
                new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        int newPosition = position % 5;
                        int childCount = mLl_circle.getChildCount();
                        for (int i = 0; i < childCount; ++i) {
                            mLl_circle.getChildAt(i).setEnabled(false);
                        }
                        mLl_circle.getChildAt(newPosition).setEnabled(true);
                        mTv_desc.setText(mContentDescs[newPosition]);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                }
        );

        new Thread() {
            @Override
            public void run() {
                mIsRunning = true;
                while (mIsRunning) {

                    try {
                        Thread.sleep(2000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d(TAG, "run: " + Thread.currentThread().getName());
                                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIsRunning = false;
    }
}
