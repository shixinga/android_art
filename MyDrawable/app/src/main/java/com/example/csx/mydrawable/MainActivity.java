package com.example.csx.mydrawable;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity1";

    TextView mTvShapeDrawablePadding;
    ImageView mIvLevelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
