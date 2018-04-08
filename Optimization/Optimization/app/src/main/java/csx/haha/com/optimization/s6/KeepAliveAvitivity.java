package csx.haha.com.optimization.s6;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by sunray on 2018-3-25.
 */

public class KeepAliveAvitivity extends Activity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "KeepAliveAvitivity----------onCreate: ");
        Window window = getWindow();
        window.setGravity(Gravity.LEFT | Gravity.TOP);
        WindowManager.LayoutParams layoutparams = window.getAttributes();
        layoutparams.width = 1;
        layoutparams.height = 1;
        layoutparams.x = 0;
        layoutparams.y = 0;
        window.setAttributes(layoutparams);

        KeepAliveActivityManager.getInstance(this).setKeepAliveActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "KeepAliveAvitivity----------onDestroy: ");
    }
}
