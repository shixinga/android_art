package csx.haha.com.optimization.utils;

import android.content.Context;

/**
 * Created by sunray on 2018-3-12.
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
