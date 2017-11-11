package com.example.csx.desktopremoteviews;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/11/8.
 */

public class MyAppWidgetProvider extends AppWidgetProvider {
    private static final String TAG = "MyAppWidgetProvider";
    public static final String ACTION_CLICK = "com.haha.action.CLICK";

    @Override
    public void onReceive(final Context context, final Intent intent) {
        Log.d(TAG, "onReceive:" + Thread.currentThread().getName());
        super.onReceive(context, intent);
        Log.d(TAG, "onReceive: action=" + intent.getAction());

        //这里判断是自己的action，做自己的事情，比如小部件被点击了要干什么，这里是做一个动画效果
        if (intent.getAction()== ACTION_CLICK ) {
            Toast.makeText(context, "click it !!", Toast.LENGTH_SHORT).show();

            new Thread() {
                @Override
                public void run() {
                    Bitmap srcBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.icon1);
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                    for (int i = 0; i < 37; ++i) {
                        float degree = (i * 10) % 360;
                        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.widget);
                        remoteViews.setImageViewBitmap(R.id.iv, rotateBitmap(context,srcBitmap,degree));
                        Intent intentClick = new Intent();
                        intentClick.setAction(ACTION_CLICK);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intentClick,0);
                        remoteViews.setOnClickPendingIntent(R.id.iv,pendingIntent);
                        appWidgetManager.updateAppWidget(new ComponentName(context,MyAppWidgetProvider.class),remoteViews);

                        SystemClock.sleep(30);
                    }
                }
            }.start();
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.d(TAG, "onUpdate: " + Thread.currentThread().getName());
        final int counter = appWidgetIds.length;
        Log.d(TAG, "onUpdate: counter=" + counter);
        for (int i = 0; i < counter; ++i) {
            int appWidgetId = appWidgetIds[i];
            onWindowUpdate(context,appWidgetManager,appWidgetId);
        }
    }

    private void onWindowUpdate(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Log.d(TAG, "onWindowUpdate: appWidgetId=" + appWidgetId);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);

        //桌面小部件 单击事件发送的Intent广播
        Intent intentClick = new Intent();
        intentClick.setAction(ACTION_CLICK);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intentClick, 0);
        remoteViews.setOnClickPendingIntent(R.id.iv, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    private Bitmap rotateBitmap(Context context, Bitmap srcBitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(degree);
        Bitmap returnBitmap = Bitmap.createBitmap(srcBitmap,0,0,srcBitmap.getWidth(),srcBitmap.getHeight(),matrix,true);
        return returnBitmap;
    }
}
