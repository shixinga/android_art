package com.shixing.l01memory.second;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by shixing on 2018/3/11.
 */

public class MyView extends View {
    private static final String TAG = "MainActivity";
    public MyView(Context context) {
        super(context);
        init();
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        ListenerCollector lc = new ListenerCollector();
        lc.setListener(this, mMyListener);
    }

    interface IMyListener {
        void callListener();
    }

    private IMyListener mMyListener = new IMyListener() {

        @Override
        public void callListener() {
            Log.d(TAG, "callListener: ");
        }
    };
}
