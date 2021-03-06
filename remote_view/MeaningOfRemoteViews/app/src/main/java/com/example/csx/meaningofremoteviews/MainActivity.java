package com.example.csx.meaningofremoteviews;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

import com.example.csx.meaningofremoteviews.utils.MyConstants;

public class MainActivity extends Activity {
    public static final String TAG = "MainActivity";

    LinearLayout mLlContent;
    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            RemoteViews remoteViews = intent.getParcelableExtra(MyConstants.EXTRA_REMOTE_VIEWS);
            Log.d(TAG, "onReceive: " + remoteViews);
            if (remoteViews != null) {
                updateUI(remoteViews);
            }
        }
    };

    private void updateUI(RemoteViews remoteViews) {

        int layoutId = getResources().getIdentifier("layout_simulated_notification", "layout", getPackageName());
        Log.d(TAG, "updateUI: layoutId=" + layoutId + "   R.layout.layout_simulated_notification=" + R.layout.layout_simulated_notification); //id是一样的
        View view = getLayoutInflater().inflate(layoutId,null);
//        View view = getLayoutInflater().inflate(layoutId,mLlContent,false);
        remoteViews.reapply(this, view);
        mLlContent.addView(view);

    }

    public void click(View view) {
        Intent intent = new Intent(this, FirstActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init() {
        mLlContent = (LinearLayout) findViewById(R.id.remote_views_content);
        IntentFilter intentFilter = new IntentFilter(MyConstants.REMOTE_ACTION);
        registerReceiver(mBroadcastReceiver,intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }
}
