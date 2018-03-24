package com.shixing.mixture.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ListView;

/**
 * Created by shixing on 2018/3/10.
 */

public class FlexableListView extends ListView {
    private int mMaxOverScrollY = 50;
    public FlexableListView(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float density = dm.density;
        mMaxOverScrollY = (int)(density * mMaxOverScrollY);
    }

    public FlexableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public FlexableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, mMaxOverScrollY, isTouchEvent);
    }
}
