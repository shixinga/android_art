package com.shixing.materialdesign_floattingactionbar_behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by shixing on 2018/5/24.
 */

public class FabBehavior extends FloatingActionButton.Behavior {
    private static final String TAG = "MainActivity";
    private boolean visible = true;

    public FabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout,
                                       FloatingActionButton child, View directTargetChild,
                                       View target, int nestedScrollAxes) {
        //当观察的View(RecyclerView)发生滑动的开始时回调，
        //nestedScrollAxes:滑动关联轴，我们只关心垂直方向的滑动
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
                || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout,
                               FloatingActionButton child, View target, int dxConsumed,
                               int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        Log.d(TAG, "onNestedScroll: ");
        // 当观察的view滑动的时候回调的
        //根据情况执行动画
        if(dyConsumed>0&&visible){
            //show
            visible = false;
            onHide(child);
        }else if(dyConsumed<0){
            //hide
            visible = true;
            onShow(child);
        }
    }

    public void onHide(FloatingActionButton fab) {
        // 隐藏动画--属性动画
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) fab.getLayoutParams();
        ViewCompat.animate(fab).scaleX(0f).scaleY(0f).start();
    }

    public void onShow(FloatingActionButton fab) {
        // 显示动画--属性动画
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) fab.getLayoutParams();
        ViewCompat.animate(fab).scaleX(1f).scaleY(1f).start();
    }
}
