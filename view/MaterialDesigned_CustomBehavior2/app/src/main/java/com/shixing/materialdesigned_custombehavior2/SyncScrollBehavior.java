package com.shixing.materialdesigned_custombehavior2;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

import android.view.View;

/**
 * 放在观察者的属性里面的
 * Created by shixing on 2018/5/25.
 */

public class SyncScrollBehavior extends CoordinatorLayout.Behavior<View> {


    public SyncScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     *
     * @param coordinatorLayout
     * @param child 观察者
     * @param directTargetChild
     * @param target 被观察者
     * @param nestedScrollAxes
     * @return
     */
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL ||
                super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    /**
     *
     * @param coordinatorLayout
     * @param child 观察者
     * @param target 被观察者
     * @param dx
     * @param dy
     * @param consumed
     */
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        int scrollY = target.getScrollY();
        child.setScrollY(scrollY);
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
    }

    /**
     * 惯性滑动时被调用
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param velocityX
     * @param velocityY
     * @param consumed
     * @return
     */
    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY, boolean consumed) {
        //快速滑动的惯性移动（松开手指时还在滑动）
        ((NestedScrollView)child).fling((int) velocityY);
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }
}
