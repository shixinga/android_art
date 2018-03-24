package com.shixing.mixture.third;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.shixing.mixture.MainActivity;
import com.shixing.mixture.R;
import com.shixing.mixture.adapter.MyExpandableAdapter;
import com.shixing.mixture.model.Group;
import com.shixing.mixture.model.People;
import com.shixing.mixture.view.MyExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shixing on 2018/2/20.
 */

public class T3SameDirectionActivity extends Activity {

    MyExpandableAdapter mExpandableAdapter;
    MyExpandableListView mExpandableListView;
    List<Group> mParents = new ArrayList<>();
    List<List<People>> mChildren = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t3_same_direction);

        initData();

        initView();

        initAdapter();
    }

    private void initData() {

        for (int i = 0;i < 3; ++i) {
            mParents.add(new Group("group-" + i));
            List<People> childTemp = new ArrayList<>();
            for (int j = 0; j < 16; ++j) {
                if (i == 0) {
                    childTemp.add(new People("xxx:" + j, 111, "aaa-" + j ));
                } else if (i == 1) {
                    childTemp.add(new People("yyy:" + j, 222, "bbb-" + j));
                } else if (i == 2) {
                    childTemp.add(new People("zzzz:" + j, 333, "ccc-" + j));
                }
            }
            mChildren.add(childTemp);
        }

    }

    private void initView() {
        mExpandableListView = (MyExpandableListView) findViewById(R.id.melv);
    }

    private void initAdapter() {
        mExpandableAdapter = new MyExpandableAdapter(this,mParents,mChildren);
        mExpandableListView.setAdapter(mExpandableAdapter);

        mExpandableListView.setmIOnHeaderUpdateListener(new MyExpandableListView.IOnHeaderUpdateListener() {
            @Override
            public View getHeaderView() {
                View headerView = LayoutInflater.from(T3SameDirectionActivity.this).inflate(R.layout.layout_parent_t3_same_direction, null);
                headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                return headerView;
            }

            @Override
            public void updateHeaderView(View view, int firstVisibleGroupPos) {
                Group group = (Group) mExpandableAdapter.getGroup(firstVisibleGroupPos);
                TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
                tv_title.setText(group.getTitle());

            }
        });

    }
}
