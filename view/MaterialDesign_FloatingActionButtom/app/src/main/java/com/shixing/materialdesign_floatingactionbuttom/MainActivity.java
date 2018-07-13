package com.shixing.materialdesign_floatingactionbuttom;

import android.animation.ObjectAnimator;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements HideScrollListener {
    private static final String TAG = "MainActivity";
    @BindView(R.id.recyclerview)
    RecyclerView mRv;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        setTitle("一个帅哥");

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            list.add("Item"+i);
        }

        mRv.addOnScrollListener(new FabScrollListener(this));
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setAdapter(new FabRecyclerAdapter(list));
    }

    private boolean reverse = false;
    public void rotate(View view) {
        float toDegree = reverse ? -180 : 180;
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 0, toDegree)
                .setDuration(400);
        animator.start();
        reverse = !reverse;
    }

    @Override
    public void onHide() {

        //隐藏动画
        mToolbar.animate().translationY(-mToolbar.getHeight()).setInterpolator(new AccelerateInterpolator(3));
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mFab.getLayoutParams();
        mFab.animate().translationY(layoutParams.height + layoutParams.bottomMargin).setInterpolator(new AccelerateInterpolator(3));
        Log.d(TAG, "onHide: layoutParams.height=" + layoutParams.height + ",getHeight()=" + mFab.getHeight()
                + ",getMeasureHeight()=" + mFab.getMeasuredHeight()
                + ",paddingTop()= " + mFab.getPaddingTop() + ", paddingBottom()=" + mFab.getPaddingBottom());
    }

    @Override
    public void onShow() {
        mToolbar.animate().translationY(0).setInterpolator(new AccelerateInterpolator(3));
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mFab.getLayoutParams();
        mFab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(3));
    }
}
