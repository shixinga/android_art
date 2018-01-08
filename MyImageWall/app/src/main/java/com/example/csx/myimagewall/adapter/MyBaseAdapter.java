package com.example.csx.myimagewall.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.csx.myimagewall.MainActivity;
import com.example.csx.myimagewall.R;
import com.example.csx.myimagewall.loader.ImageResizer;
import com.example.csx.myimagewall.loader.MyImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/11/21.
 */

public class MyBaseAdapter extends BaseAdapter {
    List<String> mDatas;
    LayoutInflater mLayoutInflater;
    MyImageLoader mMyImageLoader;
    boolean mCanGetBitmapFromNetwork;
    int mImageWidth = 0;
    public MyBaseAdapter(List<String> mDatas, Context context,boolean mCanGetBitmapFromNetwork, int mImageWidth) {
        this.mDatas = mDatas;
        this.mLayoutInflater = LayoutInflater.from(context);
        mMyImageLoader = MyImageLoader.build(context);
        this.mCanGetBitmapFromNetwork = mCanGetBitmapFromNetwork;
        this.mImageWidth = mImageWidth;
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
            convertView = mLayoutInflater.inflate(R.layout.layout_item_grid, null);
            vh.vh_iv = (ImageView) convertView.findViewById(R.id.siv);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        final String tag = (String) vh.vh_iv.getTag();
        final String uri = (String) getItem(position);
        if (!uri.equals(tag)) {
            vh.vh_iv.setImageResource(R.drawable.image_default);
        }
        if (mCanGetBitmapFromNetwork) {
            vh.vh_iv.setTag(uri);
            mMyImageLoader.bindBitmap(uri, vh.vh_iv, mImageWidth, mImageWidth);
        }

//        Log.d(MainActivity.TAG, "getView: " + vh.vh_iv.getWidth() + " .." + vh.vh_iv.getHeight());
        return convertView;
    }

    class ViewHolder {
        ImageView vh_iv;
    }
}
