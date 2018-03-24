package com.shixing.mixture.ninth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.shixing.mixture.R;

import java.util.List;

/**
 * Created by shixing on 2017/11/24.
 */

public class MyBaseAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    int mReqWidth;
    List<String> mDatas;
    boolean mCanConnectNetwork;
    MyImageLoader mMyImageLoader;

    boolean mIsGridViewIdle;

    public MyBaseAdapter(Context context, int mReqWidth, List<String> mDatas, boolean mCanConnectNetwork, boolean canLoadBitmap) {
        this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mReqWidth = mReqWidth;
        this.mDatas = mDatas;
        this.mCanConnectNetwork = mCanConnectNetwork;
        mMyImageLoader = MyImageLoader.build(context);
        this.mIsGridViewIdle = canLoadBitmap;
    }

    public void setCanConnectNetwork(boolean mCanConnectNetwork) {
        this.mCanConnectNetwork = mCanConnectNetwork;
    }

    public void setIsGridViewIdle(boolean mIsGridViewIdle) {
        this.mIsGridViewIdle = mIsGridViewIdle;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.layout_item_gridview_n9, null);
            vh.iv = (ImageView) convertView.findViewById(R.id.siv);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        ImageView iv = vh.iv;
        String uri = mDatas.get(position);
        if (!uri.equals(iv.getTag())) {
            iv.setImageResource(R.drawable.image_default);
        }
        if (mCanConnectNetwork && mIsGridViewIdle) {
            iv.setTag(uri);
            mMyImageLoader.bindBitmap(iv, uri, mReqWidth, mReqWidth);
        }
        return convertView;
    }


    class ViewHolder {
        ImageView iv;
    }
}
