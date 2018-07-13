package com.shixing.itemtouch;

import android.support.v7.widget.RecyclerView;

/**
 * 类似于IView
 * Created by shixing on 2018/5/15.
 */

public interface StartDtagListener {
    public void onStartDrag(RecyclerView.ViewHolder viewHolder);
}
