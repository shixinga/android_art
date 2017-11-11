package com.example.csx.mynotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity {

    private static int sId = 1;
    int mNumber = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void one(View view) {
        sId++;
        android.support.v4.app.NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("header!")
                .setContentText("hi ni hao")
                .setAutoCancel(true);
        Intent resultIntent = new Intent(this,FirstActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(sId,builder.build());

    }

    public void two(View view) {
        mNumber++;
//        sId++;
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_remote_view);

        Notification notification = new Notification();
        notification.tickerText = "hellow";
        notification.icon = R.mipmap.ic_launcher;
        notification.flags = Notification.FLAG_AUTO_CANCEL;


        Intent intent = new Intent(this,FirstActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intent2 = new Intent(this,SecondActivity.class);
        intent2.putExtra("sid", "" + sId);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(this,0,intent2,PendingIntent.FLAG_UPDATE_CURRENT);

        remoteViews.setTextViewText(R.id.tv_msg,"你大哥的信息=" + sId + " nuber=" + mNumber);
        remoteViews.setOnClickPendingIntent(R.id.tv_open_second_activity,pendingIntent2);
//        remoteViews.setImageViewResource(R.id.iv,R.mipmap.ic_launcher);

        notification.contentIntent = pendingIntent;
        notification.contentView = remoteViews;



        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(sId,notification);
    }
}
