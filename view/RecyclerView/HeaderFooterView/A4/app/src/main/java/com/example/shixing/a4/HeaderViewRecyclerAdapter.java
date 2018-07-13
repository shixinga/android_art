package com.example.shixing.a4;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;

import java.util.ArrayList;

/**
 * Created by shixing on 2018/5/14.
 */

public class HeaderViewRecyclerAdapter extends RecyclerView.Adapter {
    private static final String TAG = "MainActivity";
    private ArrayList<View> mHeaderViewInfos;
    private ArrayList<View> mFooterViewInfos;
    private RecyclerView.Adapter mAdapter;
    public HeaderViewRecyclerAdapter(ArrayList<View> headerViewInfos,
                                     ArrayList<View> footerViewInfos, RecyclerView.Adapter adapter) {
        mAdapter = adapter;

        if (headerViewInfos == null) {
            mHeaderViewInfos  = new ArrayList<>();
        } else {
            Log.d(TAG, "HeaderViewRecyclerAdapter: "+headerViewInfos);
            mHeaderViewInfos = headerViewInfos;
        }

        if (footerViewInfos == null) {
            mFooterViewInfos = new ArrayList<>();
        } else {
            mFooterViewInfos = footerViewInfos;
            Log.d(TAG, "HeaderViewRecyclerAdapter: " + footerViewInfos);
        }

    }

    public int getHeadersCount() {
        return mHeaderViewInfos.size();
    }

    public int getFootersCount() {
        return mFooterViewInfos.size();
    }

    //判断当前条目是什么类型的， 决定渲染什么视图给什么数据
    @Override
    public int getItemViewType(int position) {
        int numHeaders = getHeadersCount();
        Log.d(TAG, "getItemViewType: " + numHeaders);
        if (position < numHeaders) { //是头部
            return RecyclerView.INVALID_TYPE; //返回这个类型纯属装B
        }
        //正常条目部分
        final int adjPosition = position - numHeaders;
        int adapterCount = 0;
        if (mAdapter != null) {
            adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                return mAdapter.getItemViewType(adjPosition);
            }
        }

        //footer部分
        return RecyclerView.INVALID_TYPE - 1;//返回这个类型纯属装B
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: " + viewType);
        // Header
        if (viewType == RecyclerView.INVALID_TYPE) {
            return new HeaderViewHolder(mHeaderViewInfos.get(0));
        } else if (viewType == RecyclerView.INVALID_TYPE - 1) {
            return new HeaderViewHolder(mFooterViewInfos.get(0));

        }
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //也要分为3个区域
        int numHeaders = getHeadersCount();
        Log.d(TAG, "onBindViewHolder: numHeaders=" + numHeaders + ", " + position);
        if (position < numHeaders) { //是头部
            return ;
        }

        //正常条目部分
        final int adjPosition = position - numHeaders;
        int adapterCount = 0;
        if (mAdapter != null) {
            adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                mAdapter.onBindViewHolder(holder, adjPosition);
                return;
            }
        }

        //FooterView

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + getFootersCount() + "," + getHeadersCount() + "," + mAdapter.getItemCount());
        if (mAdapter != null) {
            return getFootersCount() + getHeadersCount() + mAdapter.getItemCount();
        } else {
            return getFootersCount() + getHeadersCount();
        }
    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}
