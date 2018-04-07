package com.shixing.myandfix;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Created by shixing on 2018/4/5.
 */

public class Utils {
    private static final String TAG = "MainActivity";
    public static String getVersionName(Context context) {
        String versionName = "1.0.0";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;

    }

    public static void myandFixApk(String fromOterApk) {
        Log.d(TAG, "getVersionName: " + fromOterApk);

    }
}
