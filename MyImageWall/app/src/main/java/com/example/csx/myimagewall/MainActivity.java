package com.example.csx.myimagewall;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.example.csx.myimagewall.adapter.MyBaseAdapter;
import com.example.csx.myimagewall.utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    List<String> mDatas = new ArrayList<>();
    GridView mGv;
    MyBaseAdapter mMyBaseAdapter;

    boolean mCanGetBitmapFromNetwork;
    boolean mIsWifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        initView();

        initAdapter();

        //Log.d(TAG, "onCreate: " + MyUtils.getScreenDisplay(this).widthPixels + " " + getResources().getDisplayMetrics().widthPixels + "/// " + MyUtils.getScreenDisplay(this) + "===" + getResources().getDisplayMetrics());
    }

    private void initData() {
        for (int i = 0; i < 60; ++i) {
            mDatas.add("ffff" + i);
        }

        mIsWifi = MyUtils.isWifi(this);
        if (mIsWifi) {
            mCanGetBitmapFromNetwork = true;
        }
    }

    private void initView() {
        mGv = (GridView) findViewById(R.id.gl);
        if (!mIsWifi) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("要下载美女，必须开wifi要么用你流量");
            builder.setPositiveButton("确定吗", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                      mCanGetBitmapFromNetwork = true;
                }
            });

            builder.setNegativeButton("cancle", null);
            builder.show();
        }
    }

    private void initAdapter() {
        mMyBaseAdapter = new MyBaseAdapter(mDatas, this);
        mGv.setAdapter(mMyBaseAdapter);
    }
}
