package com.shixing.mixture.fifth;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.shixing.mixture.MainActivity;
import com.shixing.mixture.R;
import com.shixing.mixture.utils.MyConstants;


/**
 * Created by Administrator on 2017/11/9.
 */

public class F5FirstActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f5_first);
        Log.d(MainActivity.TAG, "onCreate: ");

    }

    public void click(View view) {

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_f5_simulated_notification);
        remoteViews.setImageViewResource(R.id.iv,R.drawable.icon1);
        remoteViews.setTextViewText(R.id.tv_msg, "msg from process Id:" + Process.myPid());

        PendingIntent pendingIntentSecondActivity = PendingIntent.getActivity(this,0,new Intent(this, F5SecondActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntentFirstActivity = PendingIntent.getActivity(this,0, new Intent(this,F5FirstActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        remoteViews.setOnClickPendingIntent(R.id.item_holder, pendingIntentSecondActivity);
        remoteViews.setOnClickPendingIntent(R.id.tv_open_self_activity, pendingIntentFirstActivity);


        Intent intent = new Intent(MyConstants.REMOTE_ACTION);
        intent.putExtra(MyConstants.EXTRA_REMOTE_VIEWS, remoteViews);
        sendBroadcast(intent);

    }
}
