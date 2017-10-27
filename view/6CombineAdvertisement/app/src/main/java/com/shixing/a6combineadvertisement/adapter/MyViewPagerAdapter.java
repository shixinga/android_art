package com.shixing.a6combineadvertisement.adapter;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shixing.a6combineadvertisement.MainActivity;

import java.util.List;

/**
 * Created by shixing on 2017/10/18.
 */

public class MyViewPagerAdapter extends PagerAdapter {
    List<ImageView> mImageViewList;

    public MyViewPagerAdapter(List<ImageView> mImageViewList) {
        this.mImageViewList = mImageViewList;
    }

    @Override
    public int getCount() {
        //可以做无限次循环滑动
        return Integer.MAX_VALUE;
//        return mImageViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
//        Log.d(MainActivity.TAG, "isViewFromObject: view=" +view + " object=" + object);
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.d(MainActivity.TAG, "instantiateItem: " + position);
        int newPosition = position % 5;
        container.addView(mImageViewList.get(newPosition));
        return mImageViewList.get(newPosition);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.d(MainActivity.TAG, "destroyItem: " + position);
        int newPosition =  position % 5;
        container.removeView(mImageViewList.get(newPosition));
    }
}
