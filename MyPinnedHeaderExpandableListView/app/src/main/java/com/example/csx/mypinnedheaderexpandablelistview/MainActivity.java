package com.example.csx.mypinnedheaderexpandablelistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView mLv;
    List<String> mDatas = new ArrayList<>();
    MyBaseAdapter mMyBaseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        initView();

        initAdapter();
    }

    private void initData() {
        for (int i = 0; i < 40; ++i) {
            mDatas.add("item=" + i);
        }
    }

    private void initView() {
        mLv = (ListView) findViewById(R.id.lv);
    }

    private void initAdapter() {
        mMyBaseAdapter = new MyBaseAdapter(this,mDatas);
        mLv.setAdapter(mMyBaseAdapter);
    }
}
