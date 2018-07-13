package com.example.shixing.a4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WrapRecyclerView recyclerView = (WrapRecyclerView) findViewById(R.id.wrv);
        TextView headerView = new TextView(this);
        headerView.setText("我是header view");
        TextView footerView = new TextView(this);
        footerView.setText("我是footerView");

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        headerView.setLayoutParams(params);

        params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        footerView.setLayoutParams(params);
        recyclerView.addHeaderView(headerView);
        recyclerView.addFooterView(footerView);


        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 30; ++i) {
            datas.add("item:" + i);
        }

        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(datas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }
}
