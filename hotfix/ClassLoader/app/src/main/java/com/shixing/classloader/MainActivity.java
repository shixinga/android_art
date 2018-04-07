package com.shixing.classloader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //下面两个路径等价
        //  /storage/emulated/0/Android/data/com.shixing.classloader/cache/loaded.apk
        //  /mnt/sdcard/Android/data/com.shixing.classloader/cache/loaded.apk
        String apkPath = getExternalCacheDir().getAbsolutePath() + "/loaded.apk";
        Log.d(TAG, "onCreate: apkPath=" + apkPath);
        loadApk(apkPath);
        Log.d(TAG, "mm onCreate: baseContext= " + getBaseContext());
        Log.d(TAG, "mm onCreate: resources = " + getResources());
    }

    public void click(View view) {
        Intent intent = new Intent(this, S2.class);
        startActivity(intent);
    }
    private void loadApk(String apkPath) {
        File optDir = getDir("opta", MODE_PRIVATE);
        Log.d(TAG, "loadApk: optDir=" + optDir.getAbsolutePath());
        Log.d(TAG, "loadApk: clssLoader=" + getClassLoader().toString());
        DexClassLoader classLoader =
                new DexClassLoader(apkPath,optDir.getAbsolutePath(),null,getClassLoader());
        try {
            Class clazz = classLoader.loadClass("com.shixing.myandfix.Utils");
            if (clazz != null) {
                Log.d(TAG, "loadApk: ");
                Object obj = clazz.newInstance();
                Method method = clazz.getMethod("myandFixApk", String.class);
                method.invoke(obj, "from class loader apk");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
