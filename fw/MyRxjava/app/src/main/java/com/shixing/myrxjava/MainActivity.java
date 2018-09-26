package com.shixing.myrxjava;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View view)
    {
        //OnSubscriable  养羊的人
        Observable.create(new OnSubscriable<String>() {
            @Override
            public void call(Subscrible<? super String> subscrible) {
                Log.i(TAG,"1");
                //拿到了铁匠的引用之后  开始命令铁匠打铁
                subscrible.onNext("开始打铁");
            }
        }).subscrible(//铁匠
                new Subscrible<String>() {
                    @Override
                    public void onNext(String s) {
                        Log.i(TAG,"2");
                        Log.i(TAG,"3   "+s);
                    }
                });
    }

    public void change(View view)
    {
        /**
         * String  代表羊
         * Bitmap   铁
         */
        Observable.create(new OnSubscriable<String>() {
            @Override
            public void call(Subscrible<? super String> subscrible) {
                Log.i(TAG,"1  养羊的人：我要铁");
                subscrible.onNext("羊");
                subscrible.onNext("猪");
                Log.i(TAG,"4  养羊的人");
            }
        }).map(
                new Func<String,Bitmap>() {
                    //羊---》》铁
                    @Override
                    public Bitmap call(String s) {
                        Bitmap bitmap= BitmapFactory.
                                decodeResource(MainActivity.this.getResources(),R.mipmap.ic_launcher);
                        Log.i(TAG,"2 开始转换  " + s + "---》铁");
                        return bitmap;
                    }
                }).subscrible(new Subscrible<Bitmap>() {
            @Override
            public void onNext(Bitmap bitmap) {
                Log.i(TAG,"3 得到了铁   "+bitmap);
            }
        });
    }
}
