package com.example.csx.mydrawable;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity1";

    TextView mTvShapeDrawablePadding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mTvShapeDrawablePadding = (TextView) findViewById(R.id.tv_shape_drawable_padding_size);

        Drawable drawable = mTvShapeDrawablePadding.getBackground();
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Log.d(TAG, "init: width=" + width + " height=" + height);
    }

    public void click(View view) {

    }
}
