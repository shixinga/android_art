package com.example.csx.myanimation;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void secondActivityClick(View view) {

        Intent intent = new Intent(this, SecondActivity.class);
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
        Intent intent = new Intent(this, ListViewActivity.class);
        startActivity(intent);
    }

    public void animatorClick(View view) {
        Intent intent = new Intent(this, AnimatorActivity.class);
        startActivity(intent);
    }

}
