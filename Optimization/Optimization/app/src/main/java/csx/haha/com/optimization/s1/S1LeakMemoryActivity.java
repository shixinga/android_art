package csx.haha.com.optimization.s1;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.ref.WeakReference;

import csx.haha.com.optimization.R;
import csx.haha.com.optimization.utils.CommonUtil;

/**
 * Created by sunray on 2018-3-12.
 */

public class S1LeakMemoryActivity extends Activity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s1_leakmemory);
        //当横竖屏切换时，MainActivity会重新创建
        //所以这里导致旧的MainActivity.this对象泄漏
        //所以能用Application的Context就用Application的，不要用MainActivity.this
        CommonUtil.getInstace(this);
        Log.d(TAG, "onCreate: ");

//        CommonUtil.getInstace(getApplicationContext()); //比上一行代码好
//        Log.d(TAG, "onCreate: " + getApplicationContext() + ".." + getApplication());


//        loadData();
    }

    int a = 2;
    private void loadData() {
        //非静态内部类(包括匿名内部类)会持有外部类的对象引用
        //即下面的Thread内部类持有MainActivity.this的引用
        //解决方法：把内部类声明为静态内部类
       /* new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        MainActivity.this.a = 3; //持有外部类对象引用的证明
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();*/

        //也是内部类持有外部类引用的例子
        /*new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                while(true) {
                    try {
                        MainActivity.this.a = 3; //持有外部类对象引用的证明
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 20000);*/

//        mHandler.sendMessageAtTime(msg,20000); //mHandler会存在20000s
    }
    //mHandler也是内部类实例的引用，会引用外部对象MainActivity.this。
    //如果Handler在Activity退出时，它可能还活着，就会持有Activity的引用，从而导致内存泄漏

    /* private Handler mHandler = new Handler() {
         @Override
         public void handleMessage(Message msg) {
             super.handleMessage(msg);
         }
     };*/
    //上面handler的解决方法:
    private static class MyHandler extends Handler {
        private WeakReference<S1LeakMemoryActivity> mWeakReference;

        public MyHandler(S1LeakMemoryActivity activity) {
            //弱引用，当内存一发生gc的时候就会回收
            this.mWeakReference = new WeakReference<S1LeakMemoryActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            S1LeakMemoryActivity tempActivity = mWeakReference.get();
            if (tempActivity == null || tempActivity.isFinishing()) {
                return ;
            }
            //访问MainActivity.this.a
            tempActivity.a = 3;
        }
    }
}
