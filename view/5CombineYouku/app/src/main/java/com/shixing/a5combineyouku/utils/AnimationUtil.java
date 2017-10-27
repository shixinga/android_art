package com.shixing.a5combineyouku.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

/**
 * Created by shixing on 2017/10/17.
 */

public class AnimationUtil {

    public static void animationOut(View view,int delay) {
        RotateAnimation rotateAnimation = new RotateAnimation(0,180,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,1.0f);
        rotateAnimation.setStartOffset(delay);
        rotate(view,rotateAnimation);
    }

    public static void animationIn(View view) {
        RotateAnimation rotateAnimation = new RotateAnimation(-180,0,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,1.0f);
        rotate(view,rotateAnimation);
    }

    private static void rotate(View view,RotateAnimation rotateAnimation) {
        rotateAnimation.setDuration(1000);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setAnimationListener(new MyAnimationListener());
        view.startAnimation(rotateAnimation);
    }


    public static int runningAnimationCount = 0;
    //AnimationListener 的作用是监听动画是否完成
    static class MyAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
            runningAnimationCount++;
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            runningAnimationCount--;
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
