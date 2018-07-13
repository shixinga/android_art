package com.shixing.mytablelayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tl)
    TabLayout mLayout;
    @BindView(R.id.vp)
    ViewPager mViewPager;
    String [] title = {
            "头条",
            "新闻",
//            "娱乐",
//            "体育",
//            "科技",
//            "美女",
//            "财经",
//            "汽车",
//            "房子",
//            "头条"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());

        mLayout.setTabsFromPagerAdapter(adapter);
        mViewPager.setAdapter(adapter);

        //方法一
        //将tabLayout选中条目和viewPager关联
//        mLayout.setOnTabSelectedListener(new OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(Tab tab) {
//                //选中时回调
//                mViewPager.setCurrentItem(tab.getPosition(), true);
//            }
//
//            @Override
//            public void onTabUnselected(Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(Tab tab) {
//
//            }
//        });
//        //将viewPager滑动和tabLayout关联
//        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mLayout));

        //方法二(里面封装了方法一的逻辑)
        mLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < mLayout.getTabCount(); ++i) {
            Tab tab = mLayout.getTabAt(i);
            View view = View.inflate(this, R.layout.bottom_navigation, null);
            TextView tv_name = view.findViewById(R.id.tv_name);
            tv_name.setText(title[i]);
            tab.setCustomView(view);

        }
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
