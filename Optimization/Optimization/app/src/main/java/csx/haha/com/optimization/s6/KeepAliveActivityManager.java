package csx.haha.com.optimization.s6;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.lang.ref.WeakReference;

/**
 * Created by sunray on 2018-3-25.
 */

public class KeepAliveActivityManager {
    private static KeepAliveActivityManager sInstance;
    private Context mContext;
    private WeakReference<Activity> activityInstance;
    public static KeepAliveActivityManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new KeepAliveActivityManager(context);
        }
        return sInstance;
    }

    private KeepAliveActivityManager(Context context) {
        mContext = context;
    }

    public void setKeepAliveActivity(Activity activity) {
        activityInstance = new WeakReference<Activity>(activity);
    }

    public void startKeepAliveActivity() {
        Intent intent = new Intent(mContext, KeepAliveAvitivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }


    public void finishKeepAliveActivity() {
        if (activityInstance != null) {
            Activity activity = activityInstance.get();
            if ( activity != null) {
                activity.finish();
            }
        }
    }
}
