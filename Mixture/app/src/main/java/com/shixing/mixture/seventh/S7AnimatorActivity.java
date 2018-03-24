package com.shixing.mixture.seventh;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import com.shixing.mixture.R;

/**
 * Created by Administrator on 2017/11/14.
 */

public class S7AnimatorActivity extends Activity {

    public static final String TAG = "AnimatorActivity";

    ObjectAnimator mColorAnimator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator_s7);
    }

    /**
     * view在animator后view的translationX或translationY会变,
     * 而view在animtion后view的translationX或translationY不会变
     * @param view
     */
    public void iCanMoveClick(View view) {

        Log.d(TAG, "iCanMoveClick: " + view.getTranslationY());
        Button bt = (Button) view;
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(bt, "translationY", 0, bt.getHeight());
        objectAnimator.start();
    }
    public void iCanMoveClickAnimation(View view) {
        Log.d(TAG, "iCanMoveClickAnimation: " + view.getTranslationY());
        Button bt = (Button) view;
        TranslateAnimation translateAnimation = new TranslateAnimation(0,0,0,bt.getHeight());
        translateAnimation.setDuration(600);
        translateAnimation.setFillAfter(true);
        view.startAnimation(translateAnimation);
    }

    //内存泄漏,解决方法：在onDestroy()设置cancel()
    public void colorChangingClick(View view) {
        mColorAnimator = ObjectAnimator.ofInt(view, "backgroundColor", 0xffff0000, 0xff0000ff);
        mColorAnimator.setDuration(3000);
        mColorAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mColorAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mColorAnimator.setEvaluator(new ArgbEvaluator());
        mColorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.d(TAG, "onAnimationUpdate: " + Thread.currentThread().getName()); //main thread
            }
        });
        mColorAnimator.start();
    }

    public void animatorSetClick(View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view, "rotationX", 0, 360),
                ObjectAnimator.ofFloat(view, "rotationY", 0, 180),
                ObjectAnimator.ofFloat(view, "rotation", 0, -90),
                ObjectAnimator.ofFloat(view, "translationX", 0, 90),
                ObjectAnimator.ofFloat(view, "translationY", 0, 90),
                ObjectAnimator.ofFloat(view, "scaleX", 1, 1.5f),
                ObjectAnimator.ofFloat(view, "scaleY", 1, 0.5f),
                ObjectAnimator.ofFloat(view, "alpha", 1, 0.25f, 1)
        );

        animatorSet.setDuration(5 * 1000);
        animatorSet.start();
    }

    public void animatorSetXmlClick(View view) {
        AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.animator_property);
        animatorSet.setTarget(view);
        animatorSet.start();
    }

    public void attributeClick(View view) {
//        方式一
//        Button bt = (Button) view;
//        ObjectAnimator.ofInt(bt, "width", 500).setDuration(5000).start();

//        方式二
        ViewWrapper viewWrapper = new ViewWrapper(view);
        ObjectAnimator.ofInt(viewWrapper, "width", 600).setDuration(4000).start();

//        方式三
//        performAnimator(view, view.getWidth(), 500);
    }

    private void performAnimator(final View view, final int start, final int end) {
        Log.d(TAG, "performAnimator: start=" + start + " getLayoutParams.width=" + view.getLayoutParams().width + " end=" + end);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            IntEvaluator intEvaluator = new IntEvaluator();
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                float fraction = animation.getAnimatedFraction();
                int shouldWidth = intEvaluator.evaluate(fraction, start, end);
                Log.d(TAG, "onAnimationUpdate: value=" + value + " value/100.0=" + value / 100.0 + " fraction=" + fraction + " shouldWidth=" + shouldWidth);

                view.getLayoutParams().width = shouldWidth;
                view.requestLayout();
            }
        });

        valueAnimator.setDuration(5000).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mColorAnimator != null) {
            mColorAnimator.cancel();
        }
    }
}
