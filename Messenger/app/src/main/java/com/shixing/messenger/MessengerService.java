package com.shixing.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by shixing on 2017/9/11.
 */

public class MessengerService extends Service {
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.d(MainActivity.TAG, "received client's message: "+msg.getData().getString("msg"));
            Messenger replyTo = msg.replyTo;
            Message replyMessage = Message.obtain();
            Bundle bundle = new Bundle();
            bundle.putString("server","服务器大哥响应小弟");
            replyMessage.setData(bundle);
            try {
                replyTo.send(replyMessage);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };
    Messenger mMessenger = new Messenger(mHandler);
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
