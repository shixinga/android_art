package com.shixing.evenbus3learn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SecondActivity extends AppCompatActivity {
    private static final String TAG = "Main";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void click(View view) {
        new Thread(){
            @Override
            public void run() {
                EventBus.getDefault().post(new FirstEvent("hahah csxxxxx"));
            }
        }.start();
    }

    /**
     * 下面的所有click都是测试sticky = true效果。
     * 先点击2,3,1,注册按钮。然后看打印结果
     * @param view
     */
    public void click1(View view) {
        Log.d(TAG, "click1: ");
        EventBus.getDefault().postSticky(new FirstEvent("1111111"));
    }
    public void click2(View view) {
        Log.d(TAG, "click2: ");
        EventBus.getDefault().postSticky(new FirstEvent("22222"));
    }
    public void click3(View view) {
        Log.d(TAG, "click3: ");
        EventBus.getDefault().postSticky(new FirstEvent("33333"));
    }

    @Subscribe(sticky = true)
    public void onEventReceiveaaa(FirstEvent firstEvent) {
        Log.d(TAG, "onEventReceiveaaa: " + firstEvent.getMsg());
    }
    public void clickregister(View view) {
        EventBus.getDefault().register(this);
    }
    public void clickunregister(View view) {
        EventBus.getDefault().unregister(this);
    }
}
