package com.shixing.mixture.seventh;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.shixing.mixture.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/13.
 */

public class S7ListViewActivity extends Activity {

    ListView mLv;
    List<String> mDatas = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_s7);

        initData();

        initView();

        initAdapter();

    }

    private void initData() {
        for (int i = 0; i < 60; ++i) {
            mDatas.add("haha : " + i);
        }
    }

    private void initView() {
        mLv = (ListView) findViewById(R.id.lv);

        //下面的代码等价于在<ListView />里面添加： android:layoutAnimation="@anim/anim_layout"
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.anim_item);
        LayoutAnimationController layoutAnimationController = new LayoutAnimationController(animation);
        layoutAnimationController.setDelay(0.5f);
        layoutAnimationController.setOrder(LayoutAnimationController.ORDER_NORMAL);
        mLv.setLayoutAnimation(layoutAnimationController);
    }

    private void initAdapter() {
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.item_listview_s7, R.id.tv_name, mDatas);
        mLv.setAdapter(arrayAdapter);
    }
}
