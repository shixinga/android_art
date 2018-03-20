package csx.haha.com.optimization.s3;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import csx.haha.com.optimization.R;
import csx.haha.com.optimization.s2.S2ChatumLatinumActivityOptimization;

/**
 * Created by sunray on 2018-3-17.
 * AlarmManager不能唤醒屏幕
 * 但是WakeLock可以
 */

public class S3AlarmManagerActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s3_alarmmanager);

        Intent intent = new Intent("nihaoa");
        intent.putExtra("msg", "去打酱油啊");
        PendingIntent pi = PendingIntent.getBroadcast(this,0,intent,0);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        //重复执行（每隔"两秒"就会发一个广播，但实际上不是，因为被小米、华为的同步心跳屏蔽了,所以时间不可预测）
//        am.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(), 2 * 1000,pi);
        //只执行一次，但是时间能预测
        am.setExact(AlarmManager.RTC_WAKEUP,System.currentTimeMillis() + 3000,pi);
    }


}
