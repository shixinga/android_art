package com.shixing.q16setwifi.aidl;

import android.os.RemoteException;

import com.shixing.q16setwifi.IWifiManager;
import com.shixing.q16setwifi.utils.NetworkSetting;

/**
 * Created by shixing on 2017/11/2.
 */

public class WifiManager extends IWifiManager.Stub {
    @Override
    public int openWifi() throws RemoteException {
        return NetworkSetting.postData();
    }

    @Override
    public int setWifiConfiguration(String name, String password) throws RemoteException {

        return NetworkSetting.putMyData(name,password);
    }
}
