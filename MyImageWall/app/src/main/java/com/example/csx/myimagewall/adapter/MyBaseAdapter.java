package com.example.csx.myimagewall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.csx.myimagewall.R;

import java.util.List;

/**
 * Created by Administrator on 2017/11/21.
 */

public class MyBaseAdapter extends BaseAdapter {
    List<String> mDatas;
    LayoutInflater mLayoutInflater;

    public MyBaseAdapter(List<String> mDatas, Context context) {
        this.mDatas = mDatas;
        this.mLayoutInflater = LayoutInflater.from(context);
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
        vh.vh_iv.setImageResource(R.drawable.image_default);

        return convertView;
    }

    class ViewHolder {
        ImageView vh_iv;
    }
}
