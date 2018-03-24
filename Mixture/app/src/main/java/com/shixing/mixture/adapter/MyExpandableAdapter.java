package com.shixing.mixture.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.shixing.mixture.R;
import com.shixing.mixture.model.Group;
import com.shixing.mixture.model.People;

import java.util.List;

/**
 * Created by Administrator on 2017/11/6.
 */

public class MyExpandableAdapter extends BaseExpandableListAdapter {
    List<Group> mParents;
    List<List<People>> mChildren;
    LayoutInflater mLayoutInflater;
    Context mContext;

    public MyExpandableAdapter(Context context, List<Group> mParents, List<List<People>> mChildren) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.mParents = mParents;
        this.mChildren = mChildren;
    }

    @Override
    public int getGroupCount() {
        return mParents.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildren.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mParents.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChildren.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder;
        if (convertView == null) {
            groupHolder = new GroupHolder();
            convertView = mLayoutInflater.inflate(R.layout.layout_parent_t3_same_direction,null);
            groupHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }

        groupHolder.tv_title.setText(mParents.get(groupPosition).getTitle());
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder childHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.layout_child_t3_same_direction,null);
            childHolder = new ChildHolder();
            childHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            childHolder.tv_age = (TextView) convertView.findViewById(R.id.tv_age);
            childHolder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
            childHolder.bt_child = (Button) convertView.findViewById(R.id.bt_child);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }
        People people = mChildren.get(groupPosition).get(childPosition);
        childHolder.tv_name.setText(people.getName());
        childHolder.tv_age.setText(people.getAge() + "");
        childHolder.tv_address.setText(people.getAddress());
        childHolder.bt_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "点击了:" + mParents.get(groupPosition).getTitle() + mChildren.get(groupPosition).get(childPosition).getName(), Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

class GroupHolder {
    TextView tv_title;
}

class ChildHolder {
    TextView tv_name;
    TextView tv_age;
    TextView tv_address;
    Button bt_child;
}
