package com.shixing.mixture.seventh;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.shixing.mixture.R;

/**
 * Created by shixing on 2018/2/21.
 */

public class S7Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s7);
    }

    public void secondActivityClick(View view) {

        Intent intent = new Intent(this, S7SecondActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
    }

    public void test(View view) {
        Button bt = (Button) view;
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_test);
        bt.startAnimation(animation);
    }

    public void frameAnimationClick(View view) {
        Button bt = (Button) view;
        bt.setBackgroundResource(R.drawable.frame_animation); //不要在activity_main.xml里面实现，因为要点击才能显示frame动画
        AnimationDrawable animationDrawable = (AnimationDrawable) bt.getBackground();
        animationDrawable.start();
    }

    public void listViewActivity(View view) {
        Intent intent = new Intent(this, S7ListViewActivity.class);
        startActivity(intent);
    }

    public void animatorClick(View view) {
        Intent intent = new Intent(this, S7AnimatorActivity.class);
        startActivity(intent);
    }
}
