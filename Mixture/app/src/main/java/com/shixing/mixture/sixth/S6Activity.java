package com.shixing.mixture.sixth;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shixing.mixture.R;
import com.shixing.mixture.drawable.CustomerDrawable;

/**
 * Created by shixing on 2018/2/21.
 */

public class S6Activity extends Activity {
    private static final String TAG = "MainActivity1";

    TextView mTvShapeDrawablePadding;
    ImageView mIvLevelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s6);
        init();
    }

    private void init() {
        mTvShapeDrawablePadding = (TextView) findViewById(R.id.tv_shape_drawable_padding_size);
        mIvLevelList = (ImageView) findViewById(R.id.iv_level_list);

        Drawable drawable = mTvShapeDrawablePadding.getBackground();
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Log.d(TAG, "init: width=" + width + " height=" + height);

        //TransitionDrawable的transition效果设置
        TextView tvTransitionDrawable = (TextView) findViewById(R.id.tv_transition);
        ((TransitionDrawable)tvTransitionDrawable.getBackground()).startTransition(1000);


        //ScaleDrawable 的level被设置为非0才能显示
        TextView tvScaleDrawable = (TextView) findViewById(R.id.tv_scale);
        ((ScaleDrawable)tvScaleDrawable.getBackground()).setLevel(10);

        //ClipDrawable 的level被设置的是被裁减的宽高
        ImageView ivClip = (ImageView) findViewById(R.id.iv_clip);
        ((ClipDrawable)ivClip.getDrawable()).setLevel(8000);     //对应于android:src="@drawable/clip_drawable"
//        ((ClipDrawable)ivClip.getBackground()).setLevel(8000);  //对应于android:background="@drawable/clip_drawable"


        //customer drawable 的设置
        TextView tvCustomerDrawable = (TextView) findViewById(R.id.tv_customer_drawable);
        CustomerDrawable customerDrawable = new CustomerDrawable(Color.parseColor("#0ac39e"));
        tvCustomerDrawable.setBackgroundDrawable(customerDrawable);
    }

    public void click(View view) {

    }

    public void setLevelClick(View view) {
        mIvLevelList.getDrawable().setLevel(2);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.d(TAG, "onWindowFocusChanged: ");
    }
}
