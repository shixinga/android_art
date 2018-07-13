package com.example.shixing.a4;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.WrapperListAdapter;

import java.util.ArrayList;

/**
 * Created by shixing on 2018/5/14.
 */

public class WrapRecyclerView extends RecyclerView {
    private static final String TAG = "Main";
    private ArrayList<View> mHeaderViewInfos = new ArrayList<>();
    private ArrayList<View> mFooterViewInfos = new ArrayList<>();
    private Adapter mAdapter;
    public WrapRecyclerView(Context context) {
        super(context);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 从listview里面copy并改写的
     *
     * @param v The view to add.
     */
    public void addHeaderView(View v) {
        mHeaderViewInfos.add(v);

        // Wrap the adapter if it wasn't already wrapped.
        if (mAdapter != null) {
            if (!(mAdapter instanceof HeaderViewRecyclerAdapter)) {
                mAdapter = new HeaderViewRecyclerAdapter(mHeaderViewInfos, mFooterViewInfos, mAdapter);
            }

        }
    }
    /**
     * 从listview里面copy并改写的
     *
     * @param v The view to add.
     */
    public void addFooterView(View v) {
        mFooterViewInfos.add(v);

        // Wrap the adapter if it wasn't already wrapped.
        if (mAdapter != null) {
            if (!(mAdapter instanceof HeaderViewRecyclerAdapter)) {
                mAdapter = new HeaderViewRecyclerAdapter(mHeaderViewInfos, mFooterViewInfos, mAdapter);
            }

        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (mHeaderViewInfos.size() > 0|| mFooterViewInfos.size() > 0) {
            Log.d(TAG, "setAdapter: ");
            mAdapter = new HeaderViewRecyclerAdapter(mHeaderViewInfos, mFooterViewInfos, adapter);
        } else {
            mAdapter = adapter;
        }
        //下面这句话一删就不会有任何显示，因为WrapRecyclerView的父类RecyclerView有一个私有的mAdapter,所以根本就不会调用你的mAdapter!
        super.setAdapter(mAdapter);

    }
}
