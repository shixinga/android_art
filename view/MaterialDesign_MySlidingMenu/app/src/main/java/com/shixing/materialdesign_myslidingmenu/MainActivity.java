package com.shixing.materialdesign_myslidingmenu;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawerlayout)
    DrawerLayout mDrawerlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        //将Actionbar替换为toolbar
        setSupportActionBar(mToolbar);


        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,
                mDrawerlayout,mToolbar, R.string.open, R.string.close);
        //同步状态
        drawerToggle.syncState();
        //给侧滑控件设置监听,做上角图标很酷炫，会变化
//        mDrawerlayout.setDrawerListener(drawerToggle);


        mDrawerlayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //滑动过程中不断回调 slideOffset:0-1
                View content = mDrawerlayout.getChildAt(0);
                View menu = drawerView;
                float scale = 1- slideOffset;
                float leftScale = (float) (1 - 0.3 * scale);
                menu.setScaleX(leftScale);
                menu.setScaleY(leftScale);

                float contentScale = (float) (0.7  + 0.3 * scale);
                content.setScaleX(contentScale);
                content.setScaleY(contentScale);
                content.setTranslationX(menu.getMeasuredWidth()*(1-scale));//0~width

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {
                //状态改变
            }
        });

    }
}
