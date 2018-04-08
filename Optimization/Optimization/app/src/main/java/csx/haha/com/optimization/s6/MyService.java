package csx.haha.com.optimization.s6;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by sunray on 2018-3-25.
 */

public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ScreenListener listener = new ScreenListener(this.getApplicationContext());
        listener.begin(new ScreenListener.ScreenStateListener() {
            @Override
            public void onScreenOn() {
                //开屏-- finish这个一个像素的Activity
                KeepAliveActivityManager.getInstance(MyService.this).finishKeepAliveActivity();
            }

            @Override
            public void onScreenOff() {
                //锁屏--启动一个像素的Activity
                KeepAliveActivityManager.getInstance(MyService.this).startKeepAliveActivity();

            }

            @Override
            public void onUserPresent() {

            }
        });
    }
}
