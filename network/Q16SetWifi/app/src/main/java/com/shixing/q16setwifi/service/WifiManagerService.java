package com.shixing.q16setwifi.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.shixing.q16setwifi.aidl.WifiManager;

/**
 * Created by shixing on 2017/11/2.
 */

public class WifiManagerService extends Service {

    WifiManager mWifiManager = new WifiManager();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mWifiManager;
    }
}
