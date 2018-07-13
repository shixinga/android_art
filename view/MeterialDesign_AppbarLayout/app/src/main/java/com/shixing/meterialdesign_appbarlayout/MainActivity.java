package com.shixing.meterialdesign_appbarlayout;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.tb)
    Toolbar mTb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mTb);
        setTitle("哥是toolbar");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            list.add("Item"+i);
        }

        mRv.setLayoutManager(new LinearLayoutManager(this));
        MyRecyclerAdapter adapter = new MyRecyclerAdapter(list);
        mRv.setAdapter(adapter);
    }

    @OnClick(R.id.fab)
    public void haha() {
        Snackbar.make(mFab, "fab 你好吗", Snackbar.LENGTH_LONG)
                .setAction("取消啊", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "快快快", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    static class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

        List<String> list;

        public MyRecyclerAdapter(List<String> list) {
            this.list = list;
        }


        @Override
        public MyRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem, parent, false);
            MyViewHolder viewHolder = new MyViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyRecyclerAdapter.MyViewHolder holder, int position) {
            holder.tv.setText(list.get(position));
        }

        @Override
        public int getItemCount() {

            return list.size();
        }

        static class MyViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.tv)
            TextView tv;
            public MyViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
