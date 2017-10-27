package com.shixing.a7viewevent;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shixing.a7viewevent.utils.MyUtils;
import com.shixing.a7viewevent.view.HoricentalScrollViewExInner;
import com.shixing.a7viewevent.view.ListViewExInner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shixing on 2017/9/14.
 */

public class DiffDirectionInnerActivity extends Activity {
    HoricentalScrollViewExInner mHorizontalScrollViewExInner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diff_direction_inner);
        mHorizontalScrollViewExInner = (HoricentalScrollViewExInner) findViewById(R.id.hsvex_inner);
        LayoutInflater layoutInflater = getLayoutInflater();
        for (int i = 0; i < 3; ++i) {
            ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.content_layout_diff_direction_inner,mHorizontalScrollViewExInner,false);
            TextView tv = (TextView) viewGroup.findViewById(R.id.tv_diff_direction_inner);
            viewGroup.setBackgroundColor(Color.rgb(200 / (i + 1), 200 / (i + 1), 0));
            viewGroup.getLayoutParams().width = MyUtils.getScreenMetrics(this).widthPixels;
            tv.setText("page ::" + i);
            createListView(viewGroup,i);
            mHorizontalScrollViewExInner.addView(viewGroup);
        }
    }

    private void createListView(ViewGroup viewGroup, int pageId) {
        ListViewExInner listView = (ListViewExInner) viewGroup.findViewById(R.id.lv_diff_direction_inner);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 66; ++i) {
            list.add("item " + i);
        }

        listView.setmHorizontalScrollViewExInner(mHorizontalScrollViewExInner);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.content_item_diff_direction,R.id.tv_item_diff_direction,list);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(DiffDirectionInnerActivity.this, "item " + position, Toast.LENGTH_SHORT).show();
            }
        });

    }


}
