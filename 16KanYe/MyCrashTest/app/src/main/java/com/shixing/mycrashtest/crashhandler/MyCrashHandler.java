package com.shixing.mycrashtest.crashhandler;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Debug;
import android.os.Environment;
import android.os.Process;
import android.util.Log;

import com.shixing.mycrashtest.MainActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by shixing on 2017/11/25.
 */

public class MyCrashHandler implements Thread.UncaughtExceptionHandler {

    public static final boolean DEBUG = true;
    public static final String PATH = Environment.getExternalStorageDirectory() + "/CrashTestMe/crashdir/";
    public static final String FILE_NAME = "crash";
    public static final String FILE_NAME_SUFFIX = ".trace";


    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultUncaughtExceptionHandler;

    private static MyCrashHandler sInstance = new MyCrashHandler();

    private MyCrashHandler() {

    }
    public static MyCrashHandler getInstance() {
        return sInstance;
    }

    public void init(Context context) {
        mContext = context;
        mDefaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {

        //把crash信息写到SDCard上
        try {
            dumpExceptionInfoToSDCard(thread, throwable);
            uploadExceptionToServer(throwable);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        //上传crash信息到服务器

        //if系统有默认处理异常的handler，就让信息处理,else我们自己把进程杀了！！！
        if (mDefaultUncaughtExceptionHandler != null) {
            mDefaultUncaughtExceptionHandler.uncaughtException(thread, throwable);
        } else {
            Process.killProcess(Process.myPid());
        }
    }

    private void dumpExceptionInfoToSDCard(Thread thread, Throwable throwable) throws PackageManager.NameNotFoundException {
        Log.d(MainActivity.TAG, "dumpExceptionInfoToSDCard: thread=" + thread.getName()
                + " currentThread()=" +Thread.currentThread().getName());
        //SD卡不可读写
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (DEBUG) {
                Log.d(MainActivity.TAG, "dumpExceptionInfoToSDCard: sdCard can't read or write");
                return;
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String currentTime = sdf.format(new Date());
        File crashDir = new File(PATH);
        if (!crashDir.exists()) {
            crashDir.mkdirs();
        }
        Log.d(MainActivity.TAG, "dumpExceptionInfoToSDCard: "+PATH + FILE_NAME + currentTime + FILE_NAME_SUFFIX);
        File crashFile = new File(PATH + FILE_NAME + currentTime + FILE_NAME_SUFFIX);
        PrintWriter pw = null;
        try {
             pw = new PrintWriter(new BufferedWriter(new FileWriter(crashFile)));
            pw.println(currentTime);
            dumpSomePhoneInformation(pw);
            pw.println();
            throwable.printStackTrace(pw);

        } catch (IOException e) {
            e.printStackTrace();
            Log.d(MainActivity.TAG, "dumpExceptionInfoToSDCard: dump crashfile failed!");
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    private void dumpSomePhoneInformation(PrintWriter pw) throws PackageManager.NameNotFoundException {
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        pw.print("app version name:");
        pw.println(pi.versionName);
        pw.print("app version code:");
        pw.println(pi.versionCode);

        //android版本号
        pw.print("OS version:");
        pw.println(Build.VERSION.RELEASE);
        pw.print("SDK ersion:");
        pw.println(Build.VERSION.SDK_INT);

        //手机制造商
        pw.print("vendor:");
        pw.println(Build.MANUFACTURER);


        //手机型号
        pw.print("model:");
        pw.println(Build.MODEL);

        //CPU架构
        pw.print("CPU ABI:");
        pw.println(Build.CPU_ABI);
    }

    private void uploadExceptionToServer(Throwable throwable) {

    }
}
