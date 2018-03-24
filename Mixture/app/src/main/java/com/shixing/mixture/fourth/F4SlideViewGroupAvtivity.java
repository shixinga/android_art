package com.shixing.mixture.fourth;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.shixing.mixture.R;
import com.shixing.mixture.view.SlideViewGroup;

/**
 * Created by shixing on 2018/2/21.
 */

public class F4SlideViewGroupAvtivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    SlideViewGroup mSvp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f4_slidemenu);
        mSvp = (SlideViewGroup) findViewById(R.id.svp);
    }

    public void onTabClick(View view) {
        switch (view.getId()) {
            case R.id.tv_focus:
                Toast.makeText(this,"focus", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_location:
                Toast.makeText(this, "location", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_news:
                Toast.makeText(this, "news", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_picture:
                Toast.makeText(this, "picture", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_read:
                Toast.makeText(this, "read", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_ties:
                Toast.makeText(this, "talk", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_ugc:
                Toast.makeText(this, "ugc", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_vote:
                Toast.makeText(this, "vote", Toast.LENGTH_SHORT).show();

                break;
        }
    }

    public void switchWhat(View view) {
        mSvp.switchContent();
    }
}
