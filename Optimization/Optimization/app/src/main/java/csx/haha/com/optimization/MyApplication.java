package csx.haha.com.optimization;

import android.app.Application;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by sunray on 2018-3-12.
 */

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        Log.d(TAG, "onCreate: ");
        LeakCanary.install(this);
    }
}
