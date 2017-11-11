// IWifiManager.aidl
package com.shixing.q16setwifi;

// Declare any non-default types here with import statements

interface IWifiManager {

    int openWifi();
    int setWifiConfiguration(String name, String password);
}
