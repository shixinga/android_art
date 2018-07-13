package com.shixing.materialdesign_floatingactionbuttom;

import android.support.v7.widget.RecyclerView;

/**
 * Created by shixing on 2018/5/23.
 */

public class FabScrollListener extends RecyclerView.OnScrollListener {
    private static final int THRESHOLD = 20;
    private int distance = 0;
    private HideScrollListener mHideScrollListener;
    private boolean visible = true; //是否可见

    public FabScrollListener(HideScrollListener mHideScrollListener) {
        this.mHideScrollListener = mHideScrollListener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        //dy:Y轴方向上的增量

        if (distance > THRESHOLD && visible) {
            //隐藏动画
            visible = false;
            mHideScrollListener.onHide();
            distance = 0;
        } else if (distance < -20 && !visible) {
            //显示动画
            visible = true;
            mHideScrollListener.onShow();
            distance = 0;
        }
        if (visible && dy > 0 || (!visible && dy < 0)) {
            distance += dy;
        }
    }
}
