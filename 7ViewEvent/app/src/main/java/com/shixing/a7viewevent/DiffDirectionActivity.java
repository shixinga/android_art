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
import com.shixing.a7viewevent.view.HoricentalScrollViewEx;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shixing on 2017/9/13.
 */

public class DiffDirectionActivity extends Activity {
    HoricentalScrollViewEx hsvex;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diff_direction);
        hsvex = (HoricentalScrollViewEx) findViewById(R.id.hsvex);
        LayoutInflater layoutInflater = getLayoutInflater();
        for (int i = 0; i < 3; ++i) {
            ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.content_layout_diff_direction,hsvex,false);
            viewGroup.setBackgroundColor(Color.rgb(255 / (i + 1), 255 / (i + 1), 0));
            viewGroup.getLayoutParams().width = MyUtils.getScreenMetrics(this).widthPixels;
            TextView tv = (TextView) viewGroup.findViewById(R.id.tv_diff_direction);
            tv.setText("page " + i);
            createList(viewGroup,i);
            hsvex.addView(viewGroup);
        }

    }

    private void createList(ViewGroup viewGroup, int pageId) {
        ListView listView = (ListView) viewGroup.findViewById(R.id.lv_diff_direction);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 50; ++i) {
            list.add("pageId=" + pageId + " item" + i);
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.content_item_diff_direction,R.id.tv_item_diff_direction,list);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(DiffDirectionActivity.this,"click " + position,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
