package com.shixing.materialdesign_animation;

import android.animation.Animator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View view) {
        Animator animator = ViewAnimationUtils.
                createCircularReveal(view, view.getWidth() / 2, view.getHeight() / 2,0, view.getHeight());
        animator.setDuration(2000).setInterpolator(new AccelerateInterpolator());
        animator.start();
    }
    public void click2(View view) {
        Animator animator = ViewAnimationUtils.
                createCircularReveal(view, 0, 0,0, view.getHeight());
        animator.setDuration(2000).setInterpolator(new AccelerateInterpolator());
        animator.start();
    }
    public void click3(View view) {
    }
}
