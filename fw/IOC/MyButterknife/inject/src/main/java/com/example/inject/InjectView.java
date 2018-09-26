package com.example.inject;

import android.app.Activity;
import android.util.Log;

/**
 * Created by shixing on 2018/8/16.
 */

public class InjectView {
    private static final String TAG = "mainActivity";
    public static void bind(Activity activity) {
        String clsName = activity.getClass().getName();
        try {
            //get class's instance by reflect
            Class<?> viewBindClazz = Class.forName(clsName + "$$ViewBinder");
            Log.d(TAG, "bind: " + viewBindClazz.getCanonicalName());
            ViewBinder viewBinder = (ViewBinder) viewBindClazz.newInstance();
            viewBinder.bind(activity);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
