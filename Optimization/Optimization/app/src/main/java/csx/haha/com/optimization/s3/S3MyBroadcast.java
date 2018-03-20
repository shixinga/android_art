package csx.haha.com.optimization.s3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by sunray on 2018-3-18.
 */

public class S3MyBroadcast extends BroadcastReceiver {
    private static final String TAG = "MainActivity";
    @Override
    public void onReceive(Context context, Intent intent) {
        String text = intent.getStringExtra("msg");
        Log.d(TAG, "onReceive: " + text);
        Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
}
