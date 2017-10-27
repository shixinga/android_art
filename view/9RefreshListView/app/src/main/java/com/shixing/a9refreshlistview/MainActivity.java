package com.shixing.a9refreshlistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
//    ListView mListView;
    RefreshListView mRefreshListView;
    List mList = new ArrayList<String>();
    MyBaseAdapter mMyBaseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        initView();

        initAdapter();
    }

    private void initView() {
//        mListView = (ListView) findViewById(R.id.lv);
        mRefreshListView = (RefreshListView) findViewById(R.id.rlv);
        mRefreshListView.setmRefreshListener(new RefreshListView.IRefreshListener() {
            @Override
            public void onReFresh() {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mList.add(0,"这是下拉的数据");
                                mMyBaseAdapter.notifyDataSetChanged();
                                mRefreshListView.afterRefreshing();
                            }
                        });
                    }
                }.start();
            }
        });

        mRefreshListView.setmRefreshFootViewListener(new RefreshListView.IRefreshFootViewListener() {
            @Override
            public void refreshFoot() {
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mList.add("这是上啦之后加载的data");
                                mMyBaseAdapter.notifyDataSetChanged();
                                mRefreshListView.afterRefreshingFoot();
                            }
                        });
                    }
                }.start();
            }
        });
    }

    private void initData() {
        for (int i = 0; i < 30; ++i) {
            mList.add("这是数据项：" + i);
        }
    }

    private void initAdapter() {
        mMyBaseAdapter = new MyBaseAdapter(mList);
        mRefreshListView.setAdapter(mMyBaseAdapter);
//        Button button = new Button(this);
//        button.setText("hahaha");
//        mListView.addHeaderView(button);
    }
}
