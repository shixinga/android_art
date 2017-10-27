package com.shixing.a7viewevent;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by shixing on 2017/9/12.
 */

public class TestActivity extends Activity {

    private static final String TAG = "TestActivity";
    public static final int DELAY_TIME = 33;
    public static final int FRAME_COUNT = 30;
    int count = 0;
    Button mBt_test_moving;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            count++;
            if (count <= FRAME_COUNT) {
                float fraction = count / (float)FRAME_COUNT;
                mBt_test_moving.scrollBy((int)(fraction * 100),0);
                mHandler.sendMessageDelayed(Message.obtain(),DELAY_TIME);
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mBt_test_moving = (Button) findViewById(R.id.bt_test_moving);
        mBt_test_moving.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(TestActivity.this, "长按", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    public void movingButton(View view) {
        //弹性滑动0：延时sendMessage达到效果
        mHandler.sendMessageDelayed(Message.obtain(),DELAY_TIME);


        Log.d(TAG, "movingButton: bt1.getLeft()=" + mBt_test_moving.getLeft() + " bt1.getTranslationX()=" + mBt_test_moving.getTranslationX()
                + " bt1.getX()=" + mBt_test_moving.getX());
        //弹性滑动方式1：属性动画缓慢滑动
//        ObjectAnimator.ofFloat(mBt_test_moving,"translationX",0,120).setDuration(1000).start();

        //快速滑动方式2：setLayoutParams滑动
       /* ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        mlp.leftMargin += 100;
        mlp.width += 100;
        view.setLayoutParams(mlp);*/

       //弹性滑动方式3（内容滑动）：ValueAnimator
       /* final int startX = 0;
        final int deltaX = 100;
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0,1).setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                mBt_test_moving.scrollTo(startX + (int)(deltaX * fraction),0);
                Log.d(TAG, "onAnimationUpdate: ");
            }
        });
        valueAnimator.start();*/
        //弹性滑动方式4：view动画滑动
       /* TranslateAnimation translateAnimation = new TranslateAnimation(0,0,0,300);
        translateAnimation.setDuration(1000);
        translateAnimation.setFillAfter(true); //保存该view移动后的影像
        mBt_test_moving.startAnimation(translateAnimation);*/
        Log.d(TAG, "movingButton: bt1.getLeft()=" + mBt_test_moving.getLeft() + " bt1.getTranslationX()=" + mBt_test_moving.getTranslationX()
                + " bt1.getX()=" + mBt_test_moving.getX());

    }
}
