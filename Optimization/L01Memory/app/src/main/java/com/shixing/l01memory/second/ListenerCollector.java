package com.shixing.l01memory.second;

import android.view.View;

import java.util.WeakHashMap;

/**
 * Created by shixing on 2018/3/11.
 */

public class ListenerCollector {

    private static WeakHashMap<View, MyView.IMyListener> sListener = new WeakHashMap<>();
    public void setListener(View view, MyView.IMyListener listener) {
        sListener.put(view, listener);
    }
}
