package com.shixing.a10viewwork;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.shixing.a10viewwork.view.CircleView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    Button mButton1;
    Button mButton2;
    Button mButton3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton1 = (Button) findViewById(R.id.button1);
        mButton2 = (Button) findViewById(R.id.button2);
        mButton3 = (Button) findViewById(R.id.button3);

        //View的measure()和Activity的生命周期不是同步运行的，所以如果View还没绘制完毕，那么View.getMeasuredHeight()得到的是0
//        Log.d(TAG, "onCreate:mButton1.getMeasuredHeight()= "+mButton1.getMeasuredHeight());
//        ObjectAnimator.ofFloat(mButton1,"translationX",0,mButton1.getMeasuredHeight()).setDuration(1000).start();

        //获取view的measure后的宽高方法2
      /*  mButton1.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "onCreate:mButton1.getMeasuredHeight()= "+mButton1.getMeasuredHeight() + " " + Thread.currentThread().getName());
                ObjectAnimator.ofFloat(mButton1,"translationX",0,mButton1.getMeasuredHeight()).setDuration(1000).start();
            }
        });*/
    }

    //获取view的measure后的宽高方法1
    /*@Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.d(TAG, "onWindowFocusChanged:mButton1.getMeasuredHeight()= "+mButton1.getMeasuredHeight());
        ObjectAnimator.ofFloat(mButton1,"translationX",0,mButton1.getMeasuredHeight()).setDuration(1000).start();
    }*/

    public void onButtonClick(View view) {
        switch(view.getId()) {
            case R.id.button1:
                Intent intent = new Intent(this,GetNomalMeasureActivity.class);
                startActivity(intent);
                break;
            case R.id.button2:
                Intent intent1 = new Intent(this,CircleViewActivity.class);
                startActivity(intent1);
                break;
            case R.id.button3:

                break;
        }
    }
}
