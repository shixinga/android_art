package com.shixing.materialdesign_tablayout_viewpager_appbarlayout;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tl)
    TabLayout mLayout;
    @BindView(R.id.vp)
    ViewPager mViewPager;
    @BindView(R.id.tb)
    Toolbar mTb;
    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;

    String [] title = {
            "头条",
            "新闻",
            "娱乐",
            "体育",
            "科技",
            "美女",
            "财经",
            "汽车",
            "房子",
            "头条"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mTb);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());

        mLayout.setTabsFromPagerAdapter(adapter);
        mViewPager.setAdapter(adapter);

        //将tabLayout选中条目和viewPager关联
        mLayout.setOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(Tab tab) {
                //选中时回调
                mViewPager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(Tab tab) {

            }
        });
        //将viewPager滑动和tabLayout关联
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mLayout));
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f = new NewsDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("title", title[position]);
            f.setArguments(bundle);
            return f;
        }
        //该方法是给TabLayout用的
        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
        @Override
        public int getCount() {
            return title.length;
        }
    }
}
