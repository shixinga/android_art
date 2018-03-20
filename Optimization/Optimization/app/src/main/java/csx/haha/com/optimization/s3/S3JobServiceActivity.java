package csx.haha.com.optimization.s3;

import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import csx.haha.com.optimization.R;

/**
 * Created by sunray on 2018-3-18.
 */

public class S3JobServiceActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s3_jobservice);

        ComponentName componentName = new ComponentName(this, MyJobService.class);
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        for (int i = 0; i < 20; ++i) {
            JobInfo jobInfo = new JobInfo.Builder(i, componentName)
                    .setMinimumLatency(2000)
                    .setOverrideDeadline(60000) //最多执行时间
//                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED) //免费的网络：wifi、蓝牙

                    /**
                     设置重试/退避策略，当一个任务调度失败的时候执行什么样的测量采取重试。
                     initialBackoffMillis:第一次尝试重试的等待时间间隔ms
                     *backoffPolicy:对应的退避策略。比如等待的间隔呈指数增长。
                     */
//                    .setBackoffCriteria(long initialBackoffMillis, int backoffPolicy)
//                    .setBackoffCriteria(JobInfo.MAX_BACKOFF_DELAY_MILLIS, JobInfo.BACKOFF_POLICY_LINEAR)
//                    .setPeriodic (long intervalMillis)//设置执行周期，每隔一段时间间隔任务最多可以执行一次。
//                    .setPeriodic(long intervalMillis,long flexMillis)//在周期执行的末端有一个flexMiliis长度的窗口期，任务就可以在这个窗口期执行。
                    //设置设备重启后，这个任务是否还要保留。需要权限：RECEIVE_BOOT_COMPLETED //ctrl+shift+y/u x
//                    .setPersisted(boolean isPersisted);
//                    .setRequiresCharging(boolean )//是否需要充电
//                    .setRequiresDeviceIdle(boolean)//是否需要等设备出于空闲状态的时候
//                    .addTriggerContentUri(uri)//监听uri对应的数据发生改变，就会触发任务的执行。
//                    .setTriggerContentMaxDelay(long duration)//设置Content发生变化一直到任务被执行中间的最大延迟时间
                    //设置Content发生变化一直到任务被执行中间的延迟。如果在这个延迟时间内content发生了改变，延迟时间会重写计算。
//                    .setTriggerContentUpdateDelay(long durationMilimms)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY) //任意的网络都行
            .build();

            jobScheduler.schedule(jobInfo);
        }
    }
}
