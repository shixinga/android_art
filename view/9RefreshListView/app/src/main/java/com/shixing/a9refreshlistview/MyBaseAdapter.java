package com.shixing.a9refreshlistview;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by shixing on 2017/10/23.
 */

public class MyBaseAdapter extends BaseAdapter {
    List<String> mList;

    public MyBaseAdapter(List mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(parent.getContext());
        textView.setText(mList.get(position));
        textView.setTextSize(18);
        return textView;
    }
}
