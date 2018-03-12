package com.shixing.l01memory.utils;

import android.content.Context;

/**
 * 内存泄漏的是Context对象！
 * Created by shixing on 2018/3/11.
 */

public class CommonUtil {
    private Context mContext;
    private static CommonUtil sInstance;
    private CommonUtil(Context context) {
        this.mContext = context;
    }

    public static CommonUtil getInstace(Context context) {
        if (sInstance == null) {
            sInstance = new CommonUtil(context);
        }
        return sInstance;
    }
}
