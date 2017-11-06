package com.example.csx.myexpandablelistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.csx.myexpandablelistview.model.Group;
import com.example.csx.myexpandablelistview.model.People;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    MyExpandableAdapter mExpandableAdapter;
    MyExpandableListView mExpandableListView;
    List<Group> mParents = new ArrayList<>();
    List<List<People>> mChildren = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

       /* for (int k = 0;k < 3; ++k) {
            Log.d(TAG, "initData: " + mParents.get(k).getTitle());
            for (int l = 0; l < 16; ++l) {
                Log.d(TAG, "initData: " + mChildren.get(k).get(l).getName());
            }
        }*/
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
                View headerView = LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_parent, null);
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
