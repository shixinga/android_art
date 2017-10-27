package com.shixing.a5combineyouku.utils;

import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Created by shixing on 2017/10/17.
 */

public class AnimatorUtil {

    public static void animatorOut(View view) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view,"rotation",0,180);
        rotate(view,objectAnimator);
    }


    public static void animatorIn(View view) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view,"rotation",-180,0);
        rotate(view,objectAnimator);
    }

    private static void rotate(View view,ObjectAnimator objectAnimator) {
        objectAnimator.setDuration(1000);
        view.setPivotX(view.getWidth() / 2);
        view.setPivotY(view.getHeight());
        objectAnimator.start();
    }
}
