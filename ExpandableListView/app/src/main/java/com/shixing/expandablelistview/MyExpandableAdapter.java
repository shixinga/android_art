package com.shixing.expandablelistview;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by shixing on 2017/9/14.
 */

public class MyExpandableAdapter extends BaseExpandableListAdapter {
    Context mContext;
    Map<String,ArrayList<String>> mDataSet;
    String[] mParent;
    public MyExpandableAdapter(Context context,Map<String, ArrayList<String>> dataSet, String [] parent) {
        this.mDataSet = dataSet;
        this.mParent = parent;
        this.mContext = context;
    }

    //获得父项的数量
    @Override
    public int getGroupCount() {
        return mDataSet.size();
    }

    //获得某个父项的子项个数
    @Override
    public int getChildrenCount(int groupPosition) {
        return mDataSet.get(mParent[groupPosition]).size();
    }

    //获得某个父项
    @Override
    public Object getGroup(int groupPosition) {
        return mDataSet.get(mParent[groupPosition]);
    }


    //获得某个子项
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mDataSet.get(mParent[groupPosition]).get(childPosition);
    }

    //获得某个父项id
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    //获得某个父项的子项id
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    //获取父项的view
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_parent,null);

        }
        convertView.setTag(R.layout.item_parent,groupPosition);
        convertView.setTag(R.layout.item_child, -1);
        TextView tv  = (TextView) convertView.findViewById(R.id.parent_title);
        tv.setText(mParent[groupPosition]);
        return convertView;
    }

    //获得子项的view
    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_child,null);

        }
        convertView.setTag(R.layout.item_parent,groupPosition);
        convertView.setTag(R.layout.item_child, childPosition);
        TextView tv  = (TextView) convertView.findViewById(R.id.child_title);
        tv.setText(mDataSet.get(mParent[groupPosition]).get(childPosition));
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"子view:" + childPosition,Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
