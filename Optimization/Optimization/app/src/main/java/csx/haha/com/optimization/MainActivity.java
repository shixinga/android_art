package csx.haha.com.optimization;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;

import java.lang.ref.WeakReference;

import csx.haha.com.optimization.s1.S1Activity;
import csx.haha.com.optimization.s2.S2Activity;
import csx.haha.com.optimization.s3.S3Activity;
import csx.haha.com.optimization.s4.S4Activity;
import csx.haha.com.optimization.s5.S5Avtivity;
import csx.haha.com.optimization.s6.S6Avitivity;

public class MainActivity extends FragmentActivity {
    public static final String TAG = "MainActivity1";

    Handler mHandler = new Handler();
    ViewStub mViewStub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SplashFragment splashFragment = new SplashFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_mainactivity, splashFragment);
        transaction.commit();
        mViewStub = (ViewStub) findViewById(R.id.content_viewstub_main);
        //1.判断当窗体加载完毕的时候执行,立马再加载真正的布局进来
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "view stub run: " + Thread.currentThread().getName());
                //将viewstub加载进来
                mViewStub.inflate();
            }
        });
        //2.判断当窗体加载完毕的时候执行,延迟一段时间做动画
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "view post run: " + Thread.currentThread().getName());
                mHandler.postDelayed(new DelayRunnable(MainActivity.this, splashFragment), 2000);
            }
        });
        //3.同时进行异步加载数据

    }

    static class DelayRunnable implements Runnable {
        private WeakReference<Context> contextRef;
        private WeakReference<SplashFragment> fragmentRef;
        public DelayRunnable(Context context, SplashFragment fragment) {
            this.contextRef = new WeakReference<Context>(context);
            this.fragmentRef = new WeakReference<SplashFragment>(fragment);
        }

        @Override
        public void run() {
            Log.d(TAG, "DelayRunnable run: " + Thread.currentThread().getName());
            //移除fragment
            if (contextRef != null) {
                SplashFragment splashFragment = fragmentRef.get();
                if (splashFragment == null) {
                    return;
                }
                FragmentActivity activity = (FragmentActivity) contextRef.get();
                FragmentTransaction transaction = activity.getSupportFragmentManager()
                        .beginTransaction();
                transaction.remove(splashFragment);
                transaction.commit();
            }
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "main onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "main onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "main onDestroy: ");
    }

    public void click1(View view) {
        Intent intent = new Intent(this,S1Activity.class);
        startActivity(intent);
    }
    public void click2(View view) {
        Intent intent = new Intent(this,S2Activity.class);
        startActivity(intent);
    }
    public void click3(View view) {
        Intent intent = new Intent(this,S3Activity.class);
        startActivity(intent);
    }
    public void click4(View view) {
        Intent intent = new Intent(this,S4Activity.class);
        startActivity(intent);
    }
    public void click5(View view) {
        Intent intent = new Intent(this, S5Avtivity.class);
        startActivity(intent);
    }
    public void click6(View view) {
        Intent intent = new Intent(this, S6Avitivity.class);
        startActivity(intent);
    }
}
