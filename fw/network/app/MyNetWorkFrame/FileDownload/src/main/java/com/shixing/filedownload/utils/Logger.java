package com.shixing.filedownload.utils;

import android.util.Log;

import java.util.Locale;

/**
 * Created by shixing on 2018/7/3.
 */

public class Logger {
    public static final boolean DEBUG = true;

    public static void debug(String tag, String message) {
        if (DEBUG) {
            Log.d(tag, message);
        }
    }
    public static void debug(String tag, String message, Object... args) {
        if (DEBUG) {
            Log.d(tag, String.format(Locale.getDefault(), message, args));
        }
    }
    public static void error(String tag, String message) {
        if (DEBUG) {
            Log.e(tag, message);
        }
    }
    public static void info(String tag, String message) {
        if (DEBUG) {
            Log.i(tag, message);
        }
    }
    public static void warn(String tag, String message) {
        if (DEBUG) {
            Log.w(tag, message);
        }
    }
}
